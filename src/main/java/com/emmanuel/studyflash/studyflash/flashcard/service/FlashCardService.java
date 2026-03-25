package com.emmanuel.studyflash.studyflash.flashcard.service;

import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import com.emmanuel.studyflash.studyflash.flashcard.dto.FlashCardRequestDTO;
import com.emmanuel.studyflash.studyflash.flashcard.dto.FlashCardResponseDTO;
import com.emmanuel.studyflash.studyflash.flashcard.dto.FlashCardUpdateRequestDTO;
import com.emmanuel.studyflash.studyflash.flashcard.dto.FlashCardUpdateResponseDTO;
import com.emmanuel.studyflash.studyflash.flashcard.repository.FlashCardRepository;
import com.emmanuel.studyflash.studyflash.session.domain.StudySession;
import com.emmanuel.studyflash.studyflash.session.repository.StudySessionRepository;
import com.emmanuel.studyflash.studyflash.shared.exception.*;
import com.emmanuel.studyflash.studyflash.studyresult.domain.StudyResult;
import com.emmanuel.studyflash.studyflash.studyresult.repository.StudyResultRepository;
import com.emmanuel.studyflash.studyflash.topic.domain.Topic;
import com.emmanuel.studyflash.studyflash.topic.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FlashCardService {

    private final FlashCardRepository flashCardRepository;
    private final TopicRepository topicRepository;
    private final StudyResultRepository studyResultRepository;
    private final StudySessionRepository studySessionRepository;

    @Transactional
    public FlashCardResponseDTO create(FlashCardRequestDTO dto, UUID userId) {
        Topic topic = topicRepository.findByIdAndSubjectUserId(dto.topicId(), userId)
                .orElseThrow(() -> new TopicNotFoundException(dto.topicId()));

        FlashCard flashCard = FlashCard.create(dto.question(), dto.answer(), topic);
        return FlashCardResponseDTO.fromEntity(flashCardRepository.save(flashCard));
    }

    @Transactional
    public void review(UUID sessionId, UUID flashcardId, boolean correct, UUID userId) {

        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        long count = studyResultRepository.countByUserIdAndAnsweredAtBetween(userId, startOfDay, endOfDay );

         if (count >= 20){
             throw new DailyReviewLimitExceededException();
         }


        StudySession session = studySessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new SessionNotFoundException(sessionId));

        if (!session.isActive()){
            throw new SessionNotActiveException();
        }

        FlashCard flashCard = flashCardRepository
                .findByIdAndTopicSubjectUserId(flashcardId, userId)
                .orElseThrow(() -> new FlashCardNotFoundException(flashcardId, userId));


        flashCard.review(correct);

        StudyResult result = StudyResult.create(
                session,
                flashCard,
                correct,
                0,
                userId
        );

        session.recordResult(result);

        studyResultRepository.save(result);
    }

    public Page<FlashCardResponseDTO> findAll(UUID userId, Pageable pageable) {
        return flashCardRepository.findByTopicSubjectUserId(userId, pageable)
                .map(FlashCardResponseDTO::fromEntity);
    }

    public FlashCardResponseDTO getById(UUID id, UUID userId) {
        FlashCard flashCard = flashCardRepository.findByIdAndTopicSubjectUserId(id, userId)
                .orElseThrow(() -> new FlashCardNotFoundException(id, userId));
        return FlashCardResponseDTO.fromEntity(flashCard);
    }

    @Transactional
    public FlashCardUpdateResponseDTO update(UUID id, FlashCardUpdateRequestDTO dto, UUID userId) {
        FlashCard flashCard = flashCardRepository.findByIdAndTopicSubjectUserId(id, userId)
                .orElseThrow(() -> new FlashCardNotFoundException(id, userId));
        flashCard.update(dto.question(), dto.answer());
        return FlashCardUpdateResponseDTO.fromEntity(flashCard);
    }

    @Transactional
    public void delete(UUID id, UUID userId) {
        FlashCard flashCard = flashCardRepository.findByIdAndTopicSubjectUserId(id, userId)
                .orElseThrow(() -> new FlashCardNotFoundException(id, userId));
        flashCardRepository.delete(flashCard);
    }

    public List<FlashCardResponseDTO> getFlashcardsForReview(UUID userId) {

        return flashCardRepository
                .findByTopicSubjectUserIdAndNextReviewDateLessThanEqual(userId, LocalDate.now())
                .stream()
                .map(FlashCardResponseDTO::fromEntity)
                .toList();

    }

}

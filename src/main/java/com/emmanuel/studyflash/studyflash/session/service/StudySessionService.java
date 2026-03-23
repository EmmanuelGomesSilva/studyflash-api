package com.emmanuel.studyflash.studyflash.session.service;

import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import com.emmanuel.studyflash.studyflash.flashcard.repository.FlashCardRepository;
import com.emmanuel.studyflash.studyflash.session.domain.StudySession;
import com.emmanuel.studyflash.studyflash.session.dto.AnswerFlashcardRequestDTO;
import com.emmanuel.studyflash.studyflash.session.dto.AnswerFlashcardResponseDTO;
import com.emmanuel.studyflash.studyflash.session.dto.StudySessionResponseDTO;
import com.emmanuel.studyflash.studyflash.session.repository.StudySessionRepository;
import com.emmanuel.studyflash.studyflash.shared.exception.FlashCardNotFoundException;
import com.emmanuel.studyflash.studyflash.shared.exception.SessionNotFoundException;
import com.emmanuel.studyflash.studyflash.shared.exception.UserNotFoundException;
import com.emmanuel.studyflash.studyflash.studyresult.domain.StudyResult;
import com.emmanuel.studyflash.studyflash.studyresult.repository.StudyResultRepository;
import com.emmanuel.studyflash.studyflash.user.domain.User;
import com.emmanuel.studyflash.studyflash.user.repository.UserRepository;
import com.emmanuel.studyflash.studyflash.util.StringNormalizer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class StudySessionService {

    private final StudySessionRepository studySessionRepository;
    private final UserRepository userRepository;
    private final FlashCardRepository flashCardRepository;
    private final StudyResultRepository studyResultRepository;

    @Transactional
    public StudySessionResponseDTO create(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        StudySession session = StudySession.create(user);
        return StudySessionResponseDTO.fromEntity(studySessionRepository.save(session));
    }

    @Transactional
    public AnswerFlashcardResponseDTO answerFlashcard(
            UUID sessionId,
            UUID userId,
            AnswerFlashcardRequestDTO dto) {

        StudySession session = studySessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new SessionNotFoundException(sessionId));

        FlashCard flashCard = flashCardRepository
                .findByIdAndTopicSubjectUserId(dto.flashcardId(), userId)
                .orElseThrow(() -> new FlashCardNotFoundException());

        boolean isCorrect = flashCard.checkAnswer(dto.userAnswer());

        flashCard.review(isCorrect);

        StudyResult studyResult = StudyResult.create(
                session,
                flashCard,
                isCorrect,
                dto.responseTime()
        );

        studyResultRepository.save(studyResult);
        session.recordResult(studyResult);

        return AnswerFlashcardResponseDTO.fromEntity(session, isCorrect);
    }

    @Transactional
    public StudySessionResponseDTO finish(UUID sessionId, UUID userId) {
        StudySession session = studySessionRepository.findByIdAndUserId(sessionId, userId)
                .orElseThrow(() -> new SessionNotFoundException(sessionId));

        session.finish();

        return StudySessionResponseDTO.fromEntity(session);
    }

}

package com.emmanuel.studyflash.studyflash.flashcard.repository;

import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlashCardRepository extends JpaRepository<FlashCard, UUID> {

    Page<FlashCard> findByTopicSubjectUserId(UUID userId, Pageable pageable);

    Optional<FlashCard> findByIdAndTopicSubjectUserId(UUID id, UUID userId);

    List<FlashCard> findByTopicSubjectUserIdAndNextReviewDateLessThanEqual(
            UUID userId,
            LocalDate today
    );
}

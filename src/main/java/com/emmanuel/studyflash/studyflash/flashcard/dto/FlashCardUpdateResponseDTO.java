package com.emmanuel.studyflash.studyflash.flashcard.dto;

import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import com.emmanuel.studyflash.studyflash.flashcard.domain.enums.DifficultyLevel;

import java.time.LocalDate;
import java.util.UUID;

public record FlashCardUpdateResponseDTO(
        UUID id,
        String question,
        String answer,
        UUID topicId,
        LocalDate createdAt,
        LocalDate nextReviewDate,
        int totalReviews,
        double accuracyRate,
        DifficultyLevel difficultyLevel
) {

    public static FlashCardUpdateResponseDTO fromEntity(FlashCard flashCard){
        return new FlashCardUpdateResponseDTO(
                flashCard.getId(),
                flashCard.getQuestion(),
                flashCard.getAnswer(),
                flashCard.getTopic() != null ? flashCard.getTopic().getId() : null,
                flashCard.getCreatedAt(),
                flashCard.getNextReviewDate(),
                flashCard.getTotalReviews(),
                flashCard.getAccuracyRate(),
                flashCard.getDifficultyLevel()
        );
    }
}

package com.emmanuel.studyflash.studyflash.flashcard.dto;

import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import com.emmanuel.studyflash.studyflash.flashcard.domain.enums.DifficultyLevel;

import java.time.LocalDate;
import java.util.UUID;

public record FlashCardResponseDTO(
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

    public static FlashCardResponseDTO fromEntity(FlashCard flashCard){
        double accuracy = Math.round(flashCard.getAccuracyRate() * 100.0) / 100.0;
        return new FlashCardResponseDTO(
                flashCard.getId(),
                flashCard.getQuestion(),
                flashCard.getAnswer(),
                flashCard.getTopic() != null ? flashCard.getTopic().getId() : null,
                flashCard.getCreatedAt(),
                flashCard.getNextReviewDate(),
                flashCard.getTotalReviews(),
                accuracy,
                flashCard.getDifficultyLevel()
        );
    }
}

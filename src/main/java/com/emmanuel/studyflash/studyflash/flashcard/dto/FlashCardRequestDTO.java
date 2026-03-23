package com.emmanuel.studyflash.studyflash.flashcard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FlashCardRequestDTO(

        @NotBlank(message = "Question is required")
        String question,

        @NotBlank(message = "Answer is required")
        String answer,

        @NotNull(message = "Topic ID is required")
        UUID topicId
) {
}

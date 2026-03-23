package com.emmanuel.studyflash.studyflash.flashcard.dto;

import jakarta.validation.constraints.NotBlank;

public record FlashCardUpdateRequestDTO(
        @NotBlank(message = "Question is required")
        String question,

        @NotBlank(message = "Answer is required")
        String answer
) {
}

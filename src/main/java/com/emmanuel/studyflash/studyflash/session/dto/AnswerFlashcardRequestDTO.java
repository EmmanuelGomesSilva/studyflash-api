package com.emmanuel.studyflash.studyflash.session.dto;

import java.util.UUID;

public record AnswerFlashcardRequestDTO(
        UUID flashcardId,
        String userAnswer,
        Integer responseTime
) {
}

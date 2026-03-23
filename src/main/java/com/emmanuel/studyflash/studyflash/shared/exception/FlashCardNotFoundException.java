package com.emmanuel.studyflash.studyflash.shared.exception;

import java.util.UUID;

public class FlashCardNotFoundException extends RuntimeException {
    public FlashCardNotFoundException(UUID topicId, UUID userId) {
        super("Flash card not found. topicId=" + topicId + ", userId=" + userId);
    }

    public FlashCardNotFoundException() {
        super("FlashcardId cannot be null");
    }

    public FlashCardNotFoundException(String mensagem) {
        super(mensagem);
    }
}

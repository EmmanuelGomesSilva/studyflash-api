package com.emmanuel.studyflash.studyflash.shared.exception;

public class FlashcardAlreadyAnsweredException extends RuntimeException {
    public FlashcardAlreadyAnsweredException() {
        super("Flashcard already answered in this session");
    }
}

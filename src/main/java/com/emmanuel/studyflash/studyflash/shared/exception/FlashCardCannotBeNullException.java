package com.emmanuel.studyflash.studyflash.shared.exception;

public class FlashCardCannotBeNullException extends RuntimeException {
    public FlashCardCannotBeNullException(String message) {
        super("Flashcard cannot be null.");
    }
}

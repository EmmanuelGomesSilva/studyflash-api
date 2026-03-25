package com.emmanuel.studyflash.studyflash.shared.exception;

public class FlashCardCannotBeNullException extends RuntimeException {
    public FlashCardCannotBeNullException() {
        super("Flashcard cannot be null.");
    }
}

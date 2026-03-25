package com.emmanuel.studyflash.studyflash.shared.exception;

public class FlashCardInactiveException extends RuntimeException {
    public FlashCardInactiveException() {
        super("Flashcard is inactive and cannot be reviewed");
    }
}

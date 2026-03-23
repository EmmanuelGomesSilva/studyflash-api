package com.emmanuel.studyflash.studyflash.shared.exception;

public class InvalidAnswerException extends RuntimeException {
    public InvalidAnswerException() {
        super("Answer cannot be empty");
    }
}

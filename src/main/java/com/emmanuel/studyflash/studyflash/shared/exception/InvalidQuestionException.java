package com.emmanuel.studyflash.studyflash.shared.exception;

public class InvalidQuestionException extends RuntimeException {
    public InvalidQuestionException() {
        super("Question cannot be empty");
    }
}

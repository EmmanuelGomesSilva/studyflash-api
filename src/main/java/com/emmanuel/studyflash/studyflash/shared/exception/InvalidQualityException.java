package com.emmanuel.studyflash.studyflash.shared.exception;

public class InvalidQualityException extends RuntimeException {
    public InvalidQualityException() {
        super("Quality must be between 0 and 5");
    }
}

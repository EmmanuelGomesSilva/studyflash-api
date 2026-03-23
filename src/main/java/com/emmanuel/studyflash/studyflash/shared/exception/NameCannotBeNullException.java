package com.emmanuel.studyflash.studyflash.shared.exception;

public class NameCannotBeNullException extends RuntimeException {
    public NameCannotBeNullException() {
        super("Name cannot be null");
    }
}

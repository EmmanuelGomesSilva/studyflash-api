package com.emmanuel.studyflash.studyflash.shared.exception;

public class SessionCannotBeNullException extends RuntimeException {
    public SessionCannotBeNullException() {
        super("Session cannot be null");
    }
}

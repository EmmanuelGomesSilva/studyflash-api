package com.emmanuel.studyflash.studyflash.shared.exception;

public class SessionNotActiveException extends RuntimeException {
    public SessionNotActiveException() {
        super("Session must be active");
    }
}

package com.emmanuel.studyflash.studyflash.shared.exception;

public class ResultDoesNotBelongToSessionException extends RuntimeException {
    public ResultDoesNotBelongToSessionException() {
        super("Result does not belong to this session");
    }
}

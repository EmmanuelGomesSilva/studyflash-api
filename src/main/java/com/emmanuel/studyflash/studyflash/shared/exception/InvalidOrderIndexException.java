package com.emmanuel.studyflash.studyflash.shared.exception;

public class InvalidOrderIndexException extends RuntimeException {
    public InvalidOrderIndexException(int index) {
        super("Invalid order index. index=" + index);
    }
}

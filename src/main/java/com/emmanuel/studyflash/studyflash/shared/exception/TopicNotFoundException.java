package com.emmanuel.studyflash.studyflash.shared.exception;

import java.util.UUID;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(UUID id) {
        super("Topic ID: " + id + " not found");
    }
}

package com.emmanuel.studyflash.studyflash.shared.exception;

public class TopicRequiredException extends RuntimeException {
    public TopicRequiredException() {
        super("Topic is required");
    }
}

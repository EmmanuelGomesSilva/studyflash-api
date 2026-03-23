package com.emmanuel.studyflash.studyflash.topic.dto;

import com.emmanuel.studyflash.studyflash.topic.domain.Topic;

import java.time.LocalDateTime;
import java.util.UUID;

public record TopicUpdateResponse(
        UUID id,
        String name,
        String description,
        Integer orderIndex,
        LocalDateTime createdAt
) {
    public static TopicUpdateResponse fromEntity(Topic topic){
        return new TopicUpdateResponse(
                topic.getId(),
                topic.getName(),
                topic.getDescription(),
                topic.getOrderIndex(),
                topic.getCreatedAt()
        );
    }
}
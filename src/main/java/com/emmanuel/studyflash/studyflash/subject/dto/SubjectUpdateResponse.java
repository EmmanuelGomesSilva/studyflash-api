package com.emmanuel.studyflash.studyflash.subject.dto;

import com.emmanuel.studyflash.studyflash.subject.domain.Subject;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubjectUpdateResponse(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt
) {
    public static SubjectUpdateResponse fromEntity(Subject subject){
        return new SubjectUpdateResponse(
                subject.getId(),
                subject.getName(),
                subject.getDescription(),
                subject.getCreatedAt()
        );
    }
}

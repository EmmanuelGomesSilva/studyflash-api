package com.emmanuel.studyflash.studyflash.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TopicRequestDTO(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Order index is required")
        Integer orderIndex,

        @NotNull(message = "Subject Id is required")
        UUID subjectId
) {
}

package com.emmanuel.studyflash.studyflash.user.dto;

import com.emmanuel.studyflash.studyflash.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String password,
        LocalDateTime creadtedAt,
        Boolean active

) {
    public static UserResponseDTO fromEntity(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getCreatedAt(),
                user.isActive()
        );
    }
}

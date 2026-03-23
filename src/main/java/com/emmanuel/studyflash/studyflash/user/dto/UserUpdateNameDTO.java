package com.emmanuel.studyflash.studyflash.user.dto;

import com.emmanuel.studyflash.studyflash.user.domain.User;

public record UserUpdateNameDTO(
        String name
) {
    public static UserUpdateNameDTO fromEntity(User user){
        return new UserUpdateNameDTO(user.getName());
    }
}

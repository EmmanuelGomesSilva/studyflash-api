package com.emmanuel.studyflash.studyflash.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UserRequestDTO(

        @NotBlank(message = "Name is required")
        String name,

        @Email(message = "Invalid email address")
        @NotBlank(message = "Email is required")
        String email,

        @Size(min = 6)
        @NotNull(message = "Password is required")
        String password
) {
}

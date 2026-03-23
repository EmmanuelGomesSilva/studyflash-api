package com.emmanuel.studyflash.studyflash.user.service;

import com.emmanuel.studyflash.studyflash.shared.exception.EmailAlreadyExistsException;
import com.emmanuel.studyflash.studyflash.shared.exception.UserNotFoundException;
import com.emmanuel.studyflash.studyflash.user.domain.User;
import com.emmanuel.studyflash.studyflash.user.dto.UserRequestDTO;
import com.emmanuel.studyflash.studyflash.user.dto.UserResponseDTO;
import com.emmanuel.studyflash.studyflash.user.dto.UserUpdateNameDTO;
import com.emmanuel.studyflash.studyflash.user.dto.UserUpdateNameRequestDTO;
import com.emmanuel.studyflash.studyflash.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }

        String hash = passwordEncoder.encode(dto.password());

        User user = User.createUser(
                dto.name(),
                dto.email(),
                hash
        );

        return UserResponseDTO.fromEntity(userRepository.save(user));
    }

    public UserResponseDTO getById(UUID id) {
        User exists = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserResponseDTO.fromEntity(exists);
    }

    @Transactional
    public UserUpdateNameDTO updateName(UUID id, UserUpdateNameRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.updateName(dto.name());
        return UserUpdateNameDTO.fromEntity(user);
    }

    @Transactional
    public void deactivateUser(UUID id) {
        User exists = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        exists.deactivate();
    }
}

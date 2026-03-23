package com.emmanuel.studyflash.studyflash.user.api;

import com.emmanuel.studyflash.studyflash.auth.security.UserPrincipal;
import com.emmanuel.studyflash.studyflash.user.dto.UserRequestDTO;
import com.emmanuel.studyflash.studyflash.user.dto.UserResponseDTO;
import com.emmanuel.studyflash.studyflash.user.dto.UserUpdateNameDTO;
import com.emmanuel.studyflash.studyflash.user.dto.UserUpdateNameRequestDTO;
import com.emmanuel.studyflash.studyflash.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getById(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(userService.getById(userPrincipal.getId()));
    }

    @PatchMapping("/me/name")
    public ResponseEntity<UserUpdateNameDTO> updateName(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid UserUpdateNameRequestDTO dto){
        return ResponseEntity.ok(userService.updateName(userPrincipal.getId(), dto));
    }

    @PatchMapping("/me/deactivate")
    public ResponseEntity<Void> deactivate(@AuthenticationPrincipal UserPrincipal userPrincipal){
        userService.deactivateUser(userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }
}

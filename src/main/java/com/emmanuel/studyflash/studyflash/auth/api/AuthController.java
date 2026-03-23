package com.emmanuel.studyflash.studyflash.auth.api;

import com.emmanuel.studyflash.studyflash.auth.dto.LoginRequestDTO;
import com.emmanuel.studyflash.studyflash.auth.dto.LoginResponseDTO;
import com.emmanuel.studyflash.studyflash.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        String token = authService.autenticar(request);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
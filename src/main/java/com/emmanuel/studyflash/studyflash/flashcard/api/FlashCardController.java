package com.emmanuel.studyflash.studyflash.flashcard.api;

import com.emmanuel.studyflash.studyflash.auth.security.UserPrincipal;
import com.emmanuel.studyflash.studyflash.flashcard.dto.*;
import com.emmanuel.studyflash.studyflash.flashcard.service.FlashCardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/flashcards")
@CrossOrigin(origins = "*")
public class FlashCardController {

    private final FlashCardService flashCardService;

    @PostMapping
    public ResponseEntity<FlashCardResponseDTO> create(
            @RequestBody @Valid FlashCardRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user){
        return ResponseEntity.status(HttpStatus.CREATED).body(flashCardService.create(dto, user.getId()));
    }

    @PostMapping("/sessions/{sessionId}/flashcards/{id}/review")
    public ResponseEntity<Void> review(
            @PathVariable UUID sessionId,
            @PathVariable UUID id,
            @RequestBody ReviewRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user) {

        flashCardService.review(sessionId, id, dto.correct(), user.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<FlashCardResponseDTO> findAll(
            @AuthenticationPrincipal UserPrincipal user,
            Pageable pageable){
        return flashCardService.findAll(user.getId(), pageable);
    }

    @GetMapping("/{id}")
    public FlashCardResponseDTO getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        return flashCardService.getById(id, userPrincipal.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashCardUpdateResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid FlashCardUpdateRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(flashCardService.update(id, dto, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal user){
        flashCardService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/review")
    public ResponseEntity<List<FlashCardResponseDTO>> getFlashcardsForReview(
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(flashCardService.getFlashcardsForReview(userPrincipal.getId()));
    }

}


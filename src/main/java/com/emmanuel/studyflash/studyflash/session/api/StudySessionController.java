package com.emmanuel.studyflash.studyflash.session.api;

import com.emmanuel.studyflash.studyflash.auth.security.UserPrincipal;
import com.emmanuel.studyflash.studyflash.session.dto.AnswerFlashcardRequestDTO;
import com.emmanuel.studyflash.studyflash.session.dto.AnswerFlashcardResponseDTO;
import com.emmanuel.studyflash.studyflash.session.dto.StudySessionResponseDTO;
import com.emmanuel.studyflash.studyflash.session.service.StudySessionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/sessions")
public class StudySessionController {

    private final StudySessionService studySessionService;

    @PostMapping
    public ResponseEntity<StudySessionResponseDTO> create(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.status(HttpStatus.CREATED
        ).body(studySessionService.create(userPrincipal.getId()));
    }

    @PostMapping("/{sessionId}/answer")
    public ResponseEntity<AnswerFlashcardResponseDTO> answerFlashcard(
            @PathVariable UUID sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid AnswerFlashcardRequestDTO dto){
        return ResponseEntity.ok(studySessionService.answerFlashcard(sessionId, userPrincipal.getId(), dto));
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<StudySessionResponseDTO> finish(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal userPrincipal){

        return ResponseEntity.ok(studySessionService.finish(id, userPrincipal.getId()));

    }



}

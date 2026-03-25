package com.emmanuel.studyflash.studyflash.subject.api;

import com.emmanuel.studyflash.studyflash.auth.security.UserPrincipal;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectRequestDTO;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectResponseDTO;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectUpdateRequest;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectUpdateResponse;
import com.emmanuel.studyflash.studyflash.subject.service.SubjectService;
import com.emmanuel.studyflash.studyflash.user.domain.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponseDTO> create(
            @RequestBody @Valid SubjectRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subjectService.create(dto, userPrincipal.getId()));

    }

    @GetMapping("/{id}")
    public SubjectResponseDTO getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        return subjectService.getById(id,  userPrincipal.getId());
    }

    @GetMapping
    public Page<SubjectResponseDTO> findAll(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Pageable pageable){
        return subjectService.findAll(userPrincipal.getId(), pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectUpdateResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid SubjectUpdateRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(subjectService.update(id, request, userPrincipal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id,  @AuthenticationPrincipal UserPrincipal userPrincipal){
        subjectService.delete(id, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }
}

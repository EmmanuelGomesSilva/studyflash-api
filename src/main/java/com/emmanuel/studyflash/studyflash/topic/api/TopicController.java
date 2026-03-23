package com.emmanuel.studyflash.studyflash.topic.api;

import com.emmanuel.studyflash.studyflash.auth.security.UserPrincipal;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicRequestDTO;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicResponseDTO;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicUpdateRequest;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicUpdateResponse;
import com.emmanuel.studyflash.studyflash.topic.service.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/topics")
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicResponseDTO> create(
            @RequestBody @Valid TopicRequestDTO dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.create(dto, userPrincipal.getId() ));
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseDTO>> findAll(
            @RequestParam UUID subjectId,
            @AuthenticationPrincipal UserPrincipal userPrincipal, Pageable pageable){
        return ResponseEntity.ok(topicService.findAll(subjectId, userPrincipal.getId(), pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicUpdateResponse> update(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid TopicUpdateRequest request){
        return ResponseEntity.ok(topicService.update(id, userPrincipal.getId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        topicService.delete(id, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }

}

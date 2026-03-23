package com.emmanuel.studyflash.studyflash.subject.service;

import com.emmanuel.studyflash.studyflash.shared.exception.SubjectNotFoundException;
import com.emmanuel.studyflash.studyflash.shared.exception.UserNotFoundException;
import com.emmanuel.studyflash.studyflash.subject.domain.Subject;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectRequestDTO;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectResponseDTO;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectUpdateRequest;
import com.emmanuel.studyflash.studyflash.subject.dto.SubjectUpdateResponse;
import com.emmanuel.studyflash.studyflash.subject.repository.SubjectRepository;
import com.emmanuel.studyflash.studyflash.user.domain.User;
import com.emmanuel.studyflash.studyflash.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Transactional
    public SubjectResponseDTO create(SubjectRequestDTO dto, UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER NÃO ENCONTRADO: " + userId));
        Subject subject = Subject.create(dto.name(), dto.description(), user);
        return SubjectResponseDTO.fromEntity(subjectRepository.save(subject));
    }

    public SubjectResponseDTO getById(UUID id, UUID userId){
        Subject subject = subjectRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new SubjectNotFoundException(id));
        return SubjectResponseDTO.fromEntity(subject);
    }

    public Page<SubjectResponseDTO> findAll(UUID id, Pageable pageable){
        return subjectRepository.findByUserId(id, pageable)
                .map(SubjectResponseDTO::fromEntity);
    }

    @Transactional
    public SubjectUpdateResponse update(UUID id, SubjectUpdateRequest request, UUID userId){
        Subject subject = subjectRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new SubjectNotFoundException(id));
        subject.update(request.name(), request.description());
        return SubjectUpdateResponse.fromEntity(subject);
    }

    @Transactional
    public void delete(UUID id, UUID userid){
        Subject subject = subjectRepository.findByIdAndUserId(id, userid)
                .orElseThrow(() -> new SubjectNotFoundException(id));
        subjectRepository.delete(subject);
    }


}

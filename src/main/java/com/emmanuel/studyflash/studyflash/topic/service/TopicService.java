package com.emmanuel.studyflash.studyflash.topic.service;

import com.emmanuel.studyflash.studyflash.shared.exception.SubjectNotFoundException;
import com.emmanuel.studyflash.studyflash.shared.exception.TopicNotFoundException;
import com.emmanuel.studyflash.studyflash.subject.domain.Subject;
import com.emmanuel.studyflash.studyflash.subject.repository.SubjectRepository;
import com.emmanuel.studyflash.studyflash.topic.domain.Topic;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicRequestDTO;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicResponseDTO;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicUpdateRequest;
import com.emmanuel.studyflash.studyflash.topic.dto.TopicUpdateResponse;
import com.emmanuel.studyflash.studyflash.topic.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public TopicResponseDTO create(TopicRequestDTO dto, UUID userId){
        Subject subject = subjectRepository.findByIdAndUserId(dto.subjectId(), userId)
                .orElseThrow(() -> new SubjectNotFoundException(dto.subjectId()));
        Topic topic = Topic.create(
                dto.name(),
                dto.description(),
                dto.orderIndex(),
                subject
        );
        return TopicResponseDTO.fromEntity(topicRepository.save(topic));
    }

    public Page<TopicResponseDTO> findAll(UUID subjectId, UUID userId, Pageable pageable){
        return topicRepository.findBySubjectIdAndSubjectUserId(subjectId, userId, pageable)
                .map(TopicResponseDTO::fromEntity);
    }

    @Transactional
    public TopicUpdateResponse update(UUID topicId, UUID userId, TopicUpdateRequest request){
        Topic topic = topicRepository.findByIdAndSubjectUserId(topicId,userId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));

        topic.update(request.name(), request.description());
        return TopicUpdateResponse.fromEntity(topic);
    }

    @Transactional
    public void delete(UUID id, UUID userId){
        Topic topic = topicRepository.findByIdAndSubjectUserId(id, userId)
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicRepository.delete(topic);
    }
}

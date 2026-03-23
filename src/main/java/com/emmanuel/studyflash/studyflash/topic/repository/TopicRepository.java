package com.emmanuel.studyflash.studyflash.topic.repository;

import com.emmanuel.studyflash.studyflash.subject.domain.Subject;
import com.emmanuel.studyflash.studyflash.topic.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    Page<Topic> findBySubjectIdAndSubjectUserId(UUID subjectId, UUID userId, Pageable pageable);
    Optional<Topic> findByIdAndSubjectUserId(UUID topicId, UUID userId);
}
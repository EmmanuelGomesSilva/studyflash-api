package com.emmanuel.studyflash.studyflash.session.repository;

import com.emmanuel.studyflash.studyflash.session.domain.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudySessionRepository extends JpaRepository<StudySession, UUID> {

    Optional<StudySession> findByIdAndUserId(UUID sessionId, UUID userId);
}

package com.emmanuel.studyflash.studyflash.studyresult.repository;

import com.emmanuel.studyflash.studyflash.studyresult.domain.StudyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface StudyResultRepository extends JpaRepository<StudyResult, UUID> {

   List<StudyResult> findBySessionUserId(UUID id);

   long countByUserIdAndAnsweredAtBetween(UUID userId, LocalDateTime start, LocalDateTime end);




}

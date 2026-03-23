package com.emmanuel.studyflash.studyflash.subject.repository;

import com.emmanuel.studyflash.studyflash.subject.domain.Subject;
import com.emmanuel.studyflash.studyflash.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    Page<Subject> findByUserId(UUID id, Pageable pageable);
    Optional<Subject> findByIdAndUserId(UUID id, UUID userid);

}

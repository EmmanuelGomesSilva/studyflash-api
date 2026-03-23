package com.emmanuel.studyflash.studyflash.studyresult.domain;


import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import com.emmanuel.studyflash.studyflash.session.domain.StudySession;
import com.emmanuel.studyflash.studyflash.shared.exception.InvalidOrderIndexException;
import com.emmanuel.studyflash.studyflash.shared.exception.SessionCannotBeNullException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "study_results")
public class StudyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "correct", nullable = false)
    private boolean correct;

    @Column(name = "response_time_seconds")
    private Integer responseTimeSeconds;

    @Column(name = "answered_at", nullable = false)
    private LocalDateTime answeredAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id")
    private StudySession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flashcard_id")
    private FlashCard flashcard;

    private static void validate(StudySession studySession, FlashCard flashCard){
        if (studySession == null){
            throw new SessionCannotBeNullException();
        }
        if (flashCard == null){
            throw new IllegalArgumentException();
        }
    }

    public static StudyResult create(FlashCard flashCard, boolean correct) {
        StudyResult result = new StudyResult();

        result.flashcard = flashCard;
        result.correct = correct;
        result.reviewedAt = LocalDateTime.now();

        return result;
    }

    public static StudyResult create(
            StudySession session,
            FlashCard flashcard,
            boolean correct,
            Integer responseTimeSeconds
    ) {

        validate(session, flashcard);

        StudyResult result = new StudyResult();

        result.session = session;
        result.flashcard = flashcard;
        result.correct = correct;
        result.responseTimeSeconds = responseTimeSeconds;
        result.answeredAt = LocalDateTime.now();

        return result;
    }
}
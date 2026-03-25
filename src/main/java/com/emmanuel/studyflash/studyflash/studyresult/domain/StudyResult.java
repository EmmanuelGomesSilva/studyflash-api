package com.emmanuel.studyflash.studyflash.studyresult.domain;


import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import com.emmanuel.studyflash.studyflash.session.domain.StudySession;
import com.emmanuel.studyflash.studyflash.shared.exception.FlashCardCannotBeNullException;
import com.emmanuel.studyflash.studyflash.shared.exception.InvalidOrderIndexException;
import com.emmanuel.studyflash.studyflash.shared.exception.SessionCannotBeNullException;
import com.emmanuel.studyflash.studyflash.user.domain.User;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id")
    private StudySession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flashcard_id")
    private FlashCard flashcard;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    private static void validate(StudySession studySession, FlashCard flashCard){
        if (studySession == null){
            throw new SessionCannotBeNullException();
        }
        if (flashCard == null){
            throw new FlashCardCannotBeNullException();
        }
    }

    public static StudyResult create(
            StudySession session,
            FlashCard flashcard,
            boolean correct,
            Integer responseTimeSeconds,
            UUID userId
    ) {

        validate(session, flashcard);

        StudyResult result = new StudyResult();

        result.session = session;
        result.flashcard = flashcard;
        result.correct = correct;
        result.responseTimeSeconds = responseTimeSeconds;
        result.answeredAt = LocalDateTime.now();
        result.userId = userId;

        return result;
    }
}
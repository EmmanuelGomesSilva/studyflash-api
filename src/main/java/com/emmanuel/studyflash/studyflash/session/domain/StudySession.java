package com.emmanuel.studyflash.studyflash.session.domain;

import com.emmanuel.studyflash.studyflash.shared.exception.FlashcardAlreadyAnsweredException;
import com.emmanuel.studyflash.studyflash.shared.exception.ResultCannotBeNullException;
import com.emmanuel.studyflash.studyflash.shared.exception.ResultDoesNotBelongToSessionException;
import com.emmanuel.studyflash.studyflash.shared.exception.SessionNotActiveException;
import com.emmanuel.studyflash.studyflash.studyresult.domain.StudyResult;
import com.emmanuel.studyflash.studyflash.user.domain.User;
import com.emmanuel.studyflash.studyflash.util.StringNormalizer;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions")
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "total_study_time_seconds")
    private Integer totalStudyTimeSeconds;

    @Column(name = "correct_answers")
    private Integer correctAnswers;

    @Column(name = "wrong_answers")
    private Integer wrongAnswers;

    @Column(name = "total_answered")
    private Integer totalAnswered;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
    private List<StudyResult> results = new ArrayList<>();


    public static StudySession create(User user){

        if (user == null){
            throw new IllegalArgumentException("User cannot be null");
        }

        StudySession session = new StudySession();

        session.startTime = LocalDateTime.now();
        session.totalStudyTimeSeconds = 0;
        session.correctAnswers = 0;
        session.wrongAnswers = 0;
        session.totalAnswered = 0;
        session.user = user;

        return session;
    }

    public void recordResult(StudyResult result) {

        if (result == null) {
            throw new ResultCannotBeNullException();
        }

        if (result.getSession() != this) {
            throw new ResultDoesNotBelongToSessionException();
        }

        if (!isActive()) {
            throw new SessionNotActiveException();
        }


        if (results.stream()
                .anyMatch(r ->
                        Objects.equals(r.getFlashcard().getId(), result.getFlashcard().getId()))) {
            throw new FlashcardAlreadyAnsweredException();
        }

        this.results.add(result);

        totalAnswered++;

        if (result.isCorrect()) {
            correctAnswers++;
        } else {
            wrongAnswers++;
        }

    }

    public void finish(){

        if (!isActive()){
            throw new SessionNotActiveException();
        }

        endTime = LocalDateTime.now();

        Duration duration = Duration.between(startTime, endTime);

        totalStudyTimeSeconds = (int) duration.getSeconds();
    }

    public boolean isActive() {
        return startTime != null && endTime == null;
    }


}

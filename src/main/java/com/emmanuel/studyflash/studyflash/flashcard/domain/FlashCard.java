package com.emmanuel.studyflash.studyflash.flashcard.domain;

import com.emmanuel.studyflash.studyflash.flashcard.domain.enums.DifficultyLevel;
import com.emmanuel.studyflash.studyflash.shared.exception.*;
import com.emmanuel.studyflash.studyflash.studyresult.domain.StudyResult;
import com.emmanuel.studyflash.studyflash.topic.domain.Topic;
import com.emmanuel.studyflash.studyflash.util.StringNormalizer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flashcards")
public class FlashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "last_review_date")
    private LocalDate lastReviewDate;

    @Column(name = "next_review_date", nullable = false)
    private LocalDate nextReviewDate;

    @Column(name = "total_reviews", nullable = false)
    private Integer totalReviews;

    @Column(name = "accuracy_rate", nullable = false)
    private Double accuracyRate;

    @Column(name = "repetitions", nullable = false)
    private Integer repetitions;

    @Column(name = "interval_days", nullable = false)
    private Integer interval;

    @Column(name = "ease_factor", nullable = false)
    private Double easeFactor;

    @Column(name = "total_correct", nullable = false)
    private Integer totalCorrect;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficultyLevel")
    private DifficultyLevel difficultyLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "flashcard", fetch = FetchType.LAZY)
    private List<StudyResult> results = new ArrayList<>();

    public void review(boolean correct) {

        if (!this.active){
            throw new FlashCardInactiveException();
        }

        int quality = correct ? 4 : 2;
        review(quality);
    }

    public void review(int quality) {
        if (quality < 0 || quality > 5) {
            throw new InvalidQualityException();
        }

        LocalDate today = LocalDate.now();

        this.totalReviews++;
        this.lastReviewDate = today;

        boolean correct = quality >= 3;

        if (correct) {
            this.totalCorrect++;
        }

        this.accuracyRate = this.totalReviews == 0
                ? 0.0
                : (this.totalCorrect * 100.0) / this.totalReviews;

        if (quality < 3) {
            this.repetitions = 0;
            this.interval = 0;
        } else {
            this.repetitions++;

            if (this.repetitions == 1) {
                this.interval = 1;
            } else if (this.repetitions == 2) {
                this.interval = 3;
            } else {
                this.interval = Math.max(1,
                        (int) Math.round(this.interval * this.easeFactor));
            }
        }

        this.easeFactor = Math.max(1.3,
                Math.min(3.0,
                        this.easeFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02))));

        this.nextReviewDate = today.plusDays(this.interval);
    }

    private static String validateQuestion(String question) {
        if (question == null || question.isBlank()) {
            throw new InvalidQuestionException();
        }
        return question;
    }

    private static String validateAnswer(String answer) {
        if (answer == null || answer.isBlank()) {
            throw new InvalidAnswerException();
        }
        return answer;
    }

    private static Topic validateTopic(Topic topic) {
        if (topic == null) {
            throw new TopicRequiredException();
        }
        return topic;
    }

    private void initializeMetrics() {
        this.lastReviewDate = null;
        this.totalReviews = 0;
        this.accuracyRate = 0.0;

        this.repetitions = 0;
        this.interval = 1;
        this.easeFactor = 2.5;
        this.totalCorrect = 0;
    }

    private void scheduleFirstReview() {
        this.nextReviewDate = LocalDate.now();
    }

    public static FlashCard create(String question, String answer, Topic topic) {
        FlashCard flashCard = new FlashCard();

        flashCard.question = validateQuestion(question);
        flashCard.answer = validateAnswer(answer);
        flashCard.topic = validateTopic(topic);
        flashCard.active = true;

        flashCard.createdAt = LocalDate.now();

        flashCard.initializeMetrics();
        flashCard.scheduleFirstReview();


        flashCard.difficultyLevel = DifficultyLevel.MEDIUM;

        return flashCard;

    }

    public void update(String question, String answer) {
        this.question = validateQuestion(question);
        this.answer = validateAnswer(answer);
    }

    public boolean checkAnswer(String userAnswer) {
        String normalizedUser = StringNormalizer.normalize(userAnswer);
        String normalizedAnswer = StringNormalizer.normalize(this.answer);

        return normalizedUser != null && normalizedUser.equalsIgnoreCase(normalizedAnswer);
    }


}

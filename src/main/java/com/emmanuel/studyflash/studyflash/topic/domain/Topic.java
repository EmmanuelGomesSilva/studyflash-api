package com.emmanuel.studyflash.studyflash.topic.domain;

import com.emmanuel.studyflash.studyflash.flashcard.domain.FlashCard;
import com.emmanuel.studyflash.studyflash.shared.exception.InvalidOrderIndexException;
import com.emmanuel.studyflash.studyflash.subject.domain.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<FlashCard> flashCards;

    public void validateIndex(){
        if (orderIndex <= 0){
            throw  new InvalidOrderIndexException(orderIndex);
        }
    }

    public static Topic create(String name, String description, Integer orderIndex, Subject subject){
        Topic topic = new Topic();

        topic.name = name;
        topic.description = description;
        topic.orderIndex = orderIndex;
        topic.createdAt = LocalDateTime.now();
        topic.subject = subject;

        topic.validateIndex();

        return topic;
    }

    public void update(String name, String description){
        this.name = name;
        this.description = description;
    }
}

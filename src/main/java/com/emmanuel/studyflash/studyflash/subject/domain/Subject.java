package com.emmanuel.studyflash.studyflash.subject.domain;

import com.emmanuel.studyflash.studyflash.topic.domain.Topic;
import com.emmanuel.studyflash.studyflash.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Topic> topics;

    public static Subject create(String name, String description,User user){
        Subject subject = new Subject();

        subject.name = name;
        subject.description = description;
        subject.createdAt = LocalDateTime.now();
        subject.user = user;

        return subject;
    }

    public void update(String name, String description){
        this.name = name;
        this.description = description;
    }




}

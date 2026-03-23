package com.emmanuel.studyflash.studyflash.user.domain;

import com.emmanuel.studyflash.studyflash.session.domain.StudySession;
import com.emmanuel.studyflash.studyflash.shared.exception.NameCannotBeNullException;
import com.emmanuel.studyflash.studyflash.subject.domain.Subject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash",nullable = false)
    private String passwordHash;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<StudySession> studySessions = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public void deactivate(){

        this.active = false;
    }

    public void updateName(String name){
        if (name == null || name.isBlank()){
            throw new NameCannotBeNullException();
        }
        this.name = name;
    }

    public static User createUser(String name,
                                  String email,
                                  String passwordHash
    ){
        User user = new User();
        user.name = name;
        user.email = email;
        user.passwordHash = passwordHash;
        user.createdAt = LocalDateTime.now();
        user.active = true;
        return user;
    }


}

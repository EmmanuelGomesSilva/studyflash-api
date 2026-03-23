package com.emmanuel.studyflash.studyflash.flashcard.domain.enums;

public enum DifficultyLevel {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String description;


    DifficultyLevel(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}

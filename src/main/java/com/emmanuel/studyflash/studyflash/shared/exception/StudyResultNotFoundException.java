package com.emmanuel.studyflash.studyflash.shared.exception;

import java.util.UUID;

public class StudyResultNotFoundException extends RuntimeException {
    public StudyResultNotFoundException(UUID id) {
        super("Study Result not found. id=" + id);
    }
}

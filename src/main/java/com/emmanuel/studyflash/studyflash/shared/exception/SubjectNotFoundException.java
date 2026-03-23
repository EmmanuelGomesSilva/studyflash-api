package com.emmanuel.studyflash.studyflash.shared.exception;

import java.util.UUID;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(String name) {
        super("Subject not found. name=" + name);
    }
    public SubjectNotFoundException(UUID id){super("Subject not found. id=" + id);}
}

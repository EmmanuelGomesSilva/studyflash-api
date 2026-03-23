package com.emmanuel.studyflash.studyflash.shared.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensagem,
        String path

) {
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String mensagem, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.mensagem = mensagem;
        this.path = path;
    }
}

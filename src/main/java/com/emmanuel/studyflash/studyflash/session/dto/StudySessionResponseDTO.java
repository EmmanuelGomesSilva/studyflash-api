package com.emmanuel.studyflash.studyflash.session.dto;

import com.emmanuel.studyflash.studyflash.session.domain.StudySession;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudySessionResponseDTO(

        UUID id,

        LocalDateTime startTime,

        LocalDateTime endTime,

        Integer totalStudyTimeSeconds,

        Integer totalAnswered,

        Integer correctAnswers,

        Integer wrongAnswers,

        double accuracy
) {

    public static StudySessionResponseDTO fromEntity(StudySession session) {

        double calculatedAccuracy = session.getTotalAnswered() == 0 ? 0.0
                : ((double) session.getCorrectAnswers() / session.getTotalAnswered()) * 100;

        return new StudySessionResponseDTO(
                session.getId(),
                session.getStartTime(),
                session.getEndTime(),
                session.getTotalStudyTimeSeconds(),
                session.getTotalAnswered(),
                session.getCorrectAnswers(),
                session.getWrongAnswers(),
                calculatedAccuracy

        );

    }

}

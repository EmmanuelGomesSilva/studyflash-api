package com.emmanuel.studyflash.studyflash.session.dto;

import com.emmanuel.studyflash.studyflash.session.domain.StudySession;

public record AnswerFlashcardResponseDTO(
        boolean correct,
        Integer totalAnswered,
        Integer correctAnswers,
        Integer wrongAnswers,
        double accuracy
) {

    public static AnswerFlashcardResponseDTO fromEntity(StudySession session, boolean correct){
        double calculatedAccuracy = session.getTotalAnswered() == 0 ? 0.0
                : ((double) session.getCorrectAnswers() / session.getTotalAnswered()) * 100;

        return new AnswerFlashcardResponseDTO(
                correct,
                session.getTotalAnswered(),
                session.getCorrectAnswers(),
                session.getWrongAnswers(),
                calculatedAccuracy
        );
    }
}

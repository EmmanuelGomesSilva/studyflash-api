package com.emmanuel.studyflash.studyflash.statistics.service;

import com.emmanuel.studyflash.studyflash.shared.exception.StudyResultNotFoundException;
import com.emmanuel.studyflash.studyflash.statistics.dto.StatisticsResponseDTO;
import com.emmanuel.studyflash.studyflash.studyresult.domain.StudyResult;
import com.emmanuel.studyflash.studyflash.studyresult.repository.StudyResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StudyResultRepository studyResultRepository;

    public StatisticsResponseDTO statistics(UUID userId) {

        List<StudyResult> results = studyResultRepository.findBySessionUserId(userId);

        int total = results.size();

        int correct = (int) results
                .stream()
                .filter(StudyResult::isCorrect)
                .count();

        int wrong = total - correct;

        double accuracy = total > 0
                ? Math.round(((double) correct / total) * 1000) / 10.0
                : 0.0;

        return new StatisticsResponseDTO(
                total,
                correct,
                wrong,
                accuracy
        );
    }
}
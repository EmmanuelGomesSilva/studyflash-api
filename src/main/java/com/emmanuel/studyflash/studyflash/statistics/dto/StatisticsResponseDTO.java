package com.emmanuel.studyflash.studyflash.statistics.dto;

public record StatisticsResponseDTO(
        int totalReviews,
        int correct,
        int wrong,
        double accuracy
) {}

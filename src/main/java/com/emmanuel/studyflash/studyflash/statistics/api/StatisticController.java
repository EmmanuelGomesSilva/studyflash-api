package com.emmanuel.studyflash.studyflash.statistics.api;

import com.emmanuel.studyflash.studyflash.auth.security.UserPrincipal;
import com.emmanuel.studyflash.studyflash.statistics.dto.StatisticsResponseDTO;
import com.emmanuel.studyflash.studyflash.statistics.service.StatisticService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping()
    public ResponseEntity<StatisticsResponseDTO> getStatistic(
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok(statisticService.statistics(userPrincipal.getId()));
    }
}

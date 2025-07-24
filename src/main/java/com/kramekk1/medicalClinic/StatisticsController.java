package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/doctor/{doctorId}")
    public DoctorVisitStatsDTO getDoctorStats(@PathVariable Long doctorId) {
        return statisticsService.getStatsForDoc(doctorId);
    }
}

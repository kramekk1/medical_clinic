package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final StatisticMapper mapper;
    private final StatisticsRepository statisticsRepository;


    public DoctorVisitStatsDTO getStatsForDoc(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + id + " not found", HttpStatus.NOT_FOUND));

        return mapper.toDTO(doctor.getStats());
    }

}

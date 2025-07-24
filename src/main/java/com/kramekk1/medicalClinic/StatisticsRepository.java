package com.kramekk1.medicalClinic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<DoctorVisitStatsEntity, Long> {


}

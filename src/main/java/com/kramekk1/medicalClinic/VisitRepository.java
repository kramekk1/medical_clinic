package com.kramekk1.medicalClinic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByPatient_Id(Long patientId);
}

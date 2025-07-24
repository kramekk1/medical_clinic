package com.kramekk1.medicalClinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    void deleteByEmail(String email);

    Page<Patient> findAll(Pageable pageable);
}

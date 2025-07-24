package com.kramekk1.medicalClinic;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    Page<Doctor> findAll(@NonNull Pageable pageable);

    List<Doctor> findAllBySpecializationType(SpecializationType specializationType);
}

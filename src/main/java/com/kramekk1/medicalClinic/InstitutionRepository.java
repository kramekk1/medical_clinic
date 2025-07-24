package com.kramekk1.medicalClinic;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Page<Institution> findAll(@NonNull Pageable pageable);

}

package com.kramekk1.medicalClinic;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    Page<Visit> findAllByPatient_Id(Long patientId, @NonNull Pageable pageable);

    Page<Visit> findAllByDoctor_Id(Long doctorId, @NonNull Pageable pageable);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.doctor.id = :doctorId " +
            "AND v.startAt < :commandEndTime " +
            "AND v.endTime > :commandStartAt ")
    List<Visit> findOverlapping(@Param("doctorId") Long doctorId,
                                @Param("commandStartAt") LocalDateTime startAt,
                                @Param("commandEndTime") LocalDateTime endTime);

    Page<Visit> findAll(@NonNull Pageable pageable);

    List<Visit> findAllByStartAtBetweenAndStatusAndDoctor_SpecializationType(LocalDateTime startAt, LocalDateTime endTime, VisitStatus status, SpecializationType specialization);

    List<Visit> findAllByStartAtBetweenAndStatus(LocalDateTime startAt, LocalDateTime endTime, VisitStatus status);

    List<Visit> findByEndTimeBeforeAndStatus(LocalDateTime time, VisitStatus excludedStatus);

    List<Visit> findAllByStatusAndDoctor_Id(VisitStatus status, Long doctorId);
}

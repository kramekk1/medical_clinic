package com.kramekk1.medicalClinic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByPatient_Id(Long patientId);

    @Query("SELECT v FROM Visit v " +
            "WHERE v.doctor.id = :doctorId " +
            "AND v.startAt < :commandEndTime " +
            "AND v.endTime > :commandStartAt ")
    List<Visit> findOverlapping(@Param("doctorId") Long doctorId,
                                @Param("commandStartAt") LocalDateTime startAt,
                                @Param("commandEndTime") LocalDateTime endTime);
}

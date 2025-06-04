package com.kramekk1.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class VisitDTO {
    private LocalDateTime startAt;
    private LocalDateTime endTime;
    private Long doctorId;
    private Long patientId;
}

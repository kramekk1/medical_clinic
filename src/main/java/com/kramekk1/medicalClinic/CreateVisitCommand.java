package com.kramekk1.medicalClinic;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateVisitCommand {
    private LocalDateTime startAt;
    private LocalDateTime endTime;
    private Long doctorId;
}

package com.kramekk1.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorVisitStatsDTO {
    private int totalVisitsCreated;
    private int unbookedVisits;
    private double unbookedVisitsPercentage;
}

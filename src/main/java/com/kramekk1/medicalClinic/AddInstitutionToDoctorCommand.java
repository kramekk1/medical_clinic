package com.kramekk1.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddInstitutionToDoctorCommand {
    private Long institutionId;
}

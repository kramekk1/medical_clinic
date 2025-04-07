package com.kramekk1.medicalClinic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateDoctorCommand {
    private String email;
    private String password;
    private String firstname;
    private String surname;
    private SpecializationType specializationType;
}

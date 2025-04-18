package com.kramekk1.medicalClinic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CreateDoctorCommand {
    private String email;
    private String password;
    private String firstname;
    private String surname;
    private SpecializationType specializationType;
    private List<Institution> institution;
}

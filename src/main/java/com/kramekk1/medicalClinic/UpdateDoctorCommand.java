package com.kramekk1.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UpdateDoctorCommand {
    private String email;
    private String password;
    private String firstname;
    private String surname;
    private SpecializationType specializationType;
    private List<Long> institutionIds;
}

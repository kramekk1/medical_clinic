package com.kramekk1.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class DoctorDTO {
    private Long id;
    private String email;
    private String firstname;
    private String surname;
    private SpecializationType specializationType;
    private List<SimpleInstitutionDTO> institutions;

}



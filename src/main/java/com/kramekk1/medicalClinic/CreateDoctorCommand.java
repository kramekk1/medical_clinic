package com.kramekk1.medicalClinic;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorCommand {
    private String email;
    private String password;
    private String firstname;
    private String surname;
    private SpecializationType specializationType;
    private String phoneNumber;
}

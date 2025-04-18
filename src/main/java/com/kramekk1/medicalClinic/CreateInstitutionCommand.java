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
public class CreateInstitutionCommand {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String postalCode;
    private String street;
    private String buildingNumber;
    private List<Doctor> doctors;
}

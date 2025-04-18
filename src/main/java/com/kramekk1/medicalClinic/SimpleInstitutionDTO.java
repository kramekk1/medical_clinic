package com.kramekk1.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class SimpleInstitutionDTO {
    private Long id;
    private String name;
    private String postalCode;
    private String address;
    private String street;
    private String buildingNumber;
}

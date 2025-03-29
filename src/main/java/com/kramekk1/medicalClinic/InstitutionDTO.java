package com.kramekk1.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class InstitutionDTO {
    private String name;
    private String postalCode;
    private String address;
}

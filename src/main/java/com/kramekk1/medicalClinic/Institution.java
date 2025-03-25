package com.kramekk1.medicalClinic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Institution {
    private String name;
    private String postalCode;
    private String address;
}

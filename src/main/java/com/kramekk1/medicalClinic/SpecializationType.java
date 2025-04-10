package com.kramekk1.medicalClinic;

import lombok.Getter;

@Getter // nie wiem czemu w bruno w body przyjmuje i zwraca dobrego jsona, ale na h2 w kolumnie SPECIALIZATION_TYPE pokazuje cyfre 0
public enum SpecializationType {

    CARDIO("Kardiologia"), GASTRO("Gastrologia");
    private final String description;

    SpecializationType(String description) {
        this.description = description;
    }
}

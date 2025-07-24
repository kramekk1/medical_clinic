package com.kramekk1.medicalClinic;

import lombok.Getter;

@Getter
public enum SpecializationType {

    CARDIO("Kardiologia"), GASTRO("Gastrologia");
    private final String description;

    SpecializationType(String description) {
        this.description = description;
    }
}

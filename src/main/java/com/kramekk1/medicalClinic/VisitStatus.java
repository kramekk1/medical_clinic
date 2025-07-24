package com.kramekk1.medicalClinic;

import lombok.Getter;

@Getter
public enum VisitStatus {
    SCHEDULED("ZAPLANOWANA"), CANCELED("ODWOŁANA"),
    COMPLETED("ZAKOŃCZONA"), FREE("WOLNA");

    private final String description;

    VisitStatus(String description) {
        this.description= description;
    }

}

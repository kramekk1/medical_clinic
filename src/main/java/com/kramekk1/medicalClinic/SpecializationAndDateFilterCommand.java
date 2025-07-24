package com.kramekk1.medicalClinic;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SpecializationAndDateFilterCommand {
    private LocalDate date;
    private SpecializationType specializationType;
}

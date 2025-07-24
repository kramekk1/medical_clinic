package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class VisitNotInQuarterHourException extends MedicalClinicException{
    public VisitNotInQuarterHourException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

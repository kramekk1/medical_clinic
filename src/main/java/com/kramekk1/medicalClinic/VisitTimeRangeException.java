package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class VisitTimeRangeException extends MedicalClinicException{
    public VisitTimeRangeException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

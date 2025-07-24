package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class VisitInThePastException extends MedicalClinicException{
    public VisitInThePastException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

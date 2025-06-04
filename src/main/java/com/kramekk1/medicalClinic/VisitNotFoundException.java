package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class VisitNotFoundException extends MedicalClinicException{
    public VisitNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class InstitutionNotFoundException extends MedicalClinicException{

    public InstitutionNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

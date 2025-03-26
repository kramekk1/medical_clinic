package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class InstitutionDataFieldNullException extends MedicalClinicException{

    public InstitutionDataFieldNullException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

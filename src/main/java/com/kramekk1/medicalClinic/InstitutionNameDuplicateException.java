package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class InstitutionNameDuplicateException extends MedicalClinicException {

    public InstitutionNameDuplicateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

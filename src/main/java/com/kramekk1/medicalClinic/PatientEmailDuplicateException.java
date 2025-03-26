package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class PatientEmailDuplicateException extends MedicalClinicException{
    public PatientEmailDuplicateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

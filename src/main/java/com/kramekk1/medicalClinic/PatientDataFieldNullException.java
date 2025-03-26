package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class PatientDataFieldNullException extends MedicalClinicException{
    public PatientDataFieldNullException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

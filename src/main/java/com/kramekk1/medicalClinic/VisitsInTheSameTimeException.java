package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class VisitsInTheSameTimeException extends MedicalClinicException{
    public VisitsInTheSameTimeException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

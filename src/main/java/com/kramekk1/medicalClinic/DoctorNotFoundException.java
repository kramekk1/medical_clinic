package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class DoctorNotFoundException extends MedicalClinicException{
    public DoctorNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

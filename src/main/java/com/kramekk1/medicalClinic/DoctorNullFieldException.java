package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class DoctorNullFieldException extends MedicalClinicException{
    public DoctorNullFieldException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

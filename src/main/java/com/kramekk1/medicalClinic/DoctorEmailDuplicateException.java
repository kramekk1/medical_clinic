package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class DoctorEmailDuplicateException extends MedicalClinicException {
    public DoctorEmailDuplicateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

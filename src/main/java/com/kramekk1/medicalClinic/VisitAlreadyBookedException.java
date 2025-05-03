package com.kramekk1.medicalClinic;

import org.springframework.http.HttpStatus;

public class VisitAlreadyBookedException extends MedicalClinicException{
    public VisitAlreadyBookedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

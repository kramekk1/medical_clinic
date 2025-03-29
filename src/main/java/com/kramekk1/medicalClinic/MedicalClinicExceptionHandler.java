package com.kramekk1.medicalClinic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MedicalClinicExceptionHandler {

    @ExceptionHandler(MedicalClinicException.class)
    protected ResponseEntity<ErrorMessage> handlePatientNotFoundException(PatientNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }

}

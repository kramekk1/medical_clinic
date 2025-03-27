package com.kramekk1.medicalClinic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MedicalClinicExceptionHandler {

    ErrorMessage errorMessage;

    @ExceptionHandler(PatientNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handlePatientNotFoundException(PatientNotFoundException ex) {
        errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }

    @ExceptionHandler(PatientDataFieldNullException.class)
    protected ResponseEntity<ErrorMessage> handlePatientDataFieldNullException(PatientDataFieldNullException ex) {
        errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }

    @ExceptionHandler(PatientEmailDuplicateException.class)
    protected ResponseEntity<ErrorMessage> handlePatientEmailDuplicateException(PatientEmailDuplicateException ex) {
        errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }

    @ExceptionHandler(InstitutionNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleInstitutionNotFoundException(InstitutionNotFoundException ex) {
        errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }

    @ExceptionHandler(InstitutionDataFieldNullException.class)
    protected ResponseEntity<ErrorMessage> handleInstitutionDataFieldNullException(InstitutionDataFieldNullException ex) {
        errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }

    @ExceptionHandler(InstitutionNameDuplicateException.class)
    protected ResponseEntity<ErrorMessage> handleInstitutionNameDuplicateException(InstitutionNameDuplicateException ex) {
        errorMessage = new ErrorMessage(ex.getMessage(), ex.getHttpStatus(), LocalDateTime.now());
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }
}

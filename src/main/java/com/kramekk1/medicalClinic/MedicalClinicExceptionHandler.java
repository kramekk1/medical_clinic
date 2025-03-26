package com.kramekk1.medicalClinic;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MedicalClinicExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    protected ResponseEntity<String> handlePatientNotFoundException(PatientNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(PatientDataFieldNullException.class)
    protected ResponseEntity<String> handlePatientDataFieldNullException(PatientDataFieldNullException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(PatientEmailDuplicateException.class)
    protected ResponseEntity<String> handlePatientEmailDuplicateException(PatientEmailDuplicateException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InstitutionNotFoundException.class)
    protected ResponseEntity<String> handleInstitutionNotFoundException(InstitutionNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InstitutionDataFieldNullException.class)
    protected ResponseEntity<String> handleInstitutionDataFieldNullException(InstitutionDataFieldNullException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InstitutionNameDuplicateException.class)
    protected ResponseEntity<String> handleInstitutionNameDuplicateException(InstitutionNameDuplicateException ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.getHttpStatus());
    }
}

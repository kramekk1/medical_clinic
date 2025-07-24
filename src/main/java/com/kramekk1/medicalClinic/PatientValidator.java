package com.kramekk1.medicalClinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {

    public static void validateEmailDuplicate(String email, PatientRepository patientRepository) {
        Optional<Patient> patient = patientRepository.findByEmail(email);

        if (patient.isPresent()) {
            throw new PatientEmailDuplicateException("Patient with this email already exist", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateNullField(Patient newPatient) {
        if (newPatient.getEmail() == null || newPatient.getBirthday() == null || newPatient.getUser().getUsername() == null ||
                newPatient.getUser().getPassword() == null || newPatient.getFirstName() == null ||
                newPatient.getLastName() == null || newPatient.getIdCardNo() == null || newPatient.getPhoneNumber() == null) {
            throw new PatientDataFieldNullException("One or more patient data field is null", HttpStatus.BAD_REQUEST);
        }
    }

}

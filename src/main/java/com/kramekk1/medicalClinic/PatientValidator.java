package com.kramekk1.medicalClinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {

    public static void validateEmailDuplicate(String email, PatientRepository patientRepository) {
        Optional<Patient> patient = patientRepository.findPatientByEmail(email);

        if (patient.isPresent()) {
            throw new IllegalArgumentException("Patient with this email exist");
        }
    }

    public static void validateEmailNotNull(Patient patient) {
        if (patient.getEmail() == null) {
            throw new IllegalArgumentException("Patient email is null"); // ?
        }
    }

    public static void validatePatient(Optional<Patient> patient) {
        if (patient.isEmpty()) {
            throw new IllegalArgumentException("Patient not found");
        }
    }

    public static void validateNullField(Patient newPatient) {
        if (newPatient.getEmail() == null || newPatient.getBirthday() == null || newPatient.getPassword() == null ||
                newPatient.getFirstName() == null || newPatient.getLastName() == null || newPatient.getIdCardNo() == null || newPatient.getPhoneNumber() == null) {
            throw new IllegalArgumentException("One or more patient data field is null");
        }
    }
}

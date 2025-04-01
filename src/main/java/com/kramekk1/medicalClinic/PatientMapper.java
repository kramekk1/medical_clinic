package com.kramekk1.medicalClinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientMapper {

    public static PatientDTO convertToDTO(Patient patient) {
        return new PatientDTO(patient.getEmail(), patient.getFirstName(), patient.getLastName(),
                patient.getPhoneNumber(), patient.getBirthday());
    }

    public static PatientDTO convertToDTO(CreatePatientCommand patient) {
        return new PatientDTO(patient.getEmail(), patient.getFirstName(), patient.getLastName(),
                patient.getPhoneNumber(), patient.getBirthday());
    }

    public static Patient convertToEntity(CreatePatientCommand patient) {
        return new Patient(patient.getEmail(), patient.getPassword(), patient.getIdCardNo(),
                patient.getFirstName(), patient.getLastName(), patient.getPhoneNumber(), patient.getBirthday());
    }

    public static Patient convertToEntity(EditPatientCommand patient) {
        return new Patient(patient.getEmail(), patient.getPassword(), patient.getIdCardNo(),
                patient.getFirstName(), patient.getLastName(), patient.getPhoneNumber(), patient.getBirthday());
    }
}

package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public void editPatientByEmail(String email, Patient newPatient) {
        patientRepository.editPatientByEmail(email, newPatient);
    }

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }

    public void addPatient(Patient patient) {
        patientRepository.addPatient(patient);
    }

    public void deletePatient(String email) {
        patientRepository.deletePatient(email);
    }

}

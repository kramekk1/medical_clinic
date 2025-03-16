package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public void editPatientByEmail(@PathVariable String email, Patient newPatient) {
        Patient patient = patientRepository.findPatientByEmail(email).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
            patient.setFirstName(newPatient.getFirstName());
            patient.setLastName(newPatient.getLastName());
            patient.setIdCardNo(newPatient.getIdCardNo());
            patient.setBirthday(newPatient.getBirthday());
            patient.setEmail(newPatient.getEmail());
            patient.setPassword(newPatient.getPassword());
            patient.setPhoneNumber(newPatient.getPhoneNumber());
    }
}

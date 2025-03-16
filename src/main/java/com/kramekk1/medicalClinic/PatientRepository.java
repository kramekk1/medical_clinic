package com.kramekk1.medicalClinic;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {
    private final List<Patient> allPatients = new ArrayList<>();

    public List<Patient> getPatients() {
        return new ArrayList<>(allPatients);
    }
    public Optional<Patient> findPatientByEmail(@PathVariable String email) {
        return allPatients.stream()
                .filter(patient -> email.equals(patient.getEmail()))
                .findFirst();
    }
    public void addPatient(Patient patient) {
        allPatients.add(patient);
    }
    public void deletePatient(@PathVariable String email) {
        allPatients.removeIf(patient -> email.equals(patient.getEmail()));
    }

}

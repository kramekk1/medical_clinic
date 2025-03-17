package com.kramekk1.medicalClinic;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository // oznaczenie klasy - beana, która łączy logike biznesową z zasobami(w tym wypadku lista). Tutaj beda metody "wykonujące"? konkretne rzeczy, dzialajace na zasobie
// zasób to bedzie w rozumieniu np cała lista pacjentów np 100 osób, czy zasobem moze byc tez jeden chłop?
// co to ten wraper
@RequiredArgsConstructor
public class PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public Optional<Patient> findPatientByEmail(String email) {
        return patients.stream()
                .filter(patient -> email.equals(patient.getEmail()))
                .findFirst();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void deletePatient(String email) {
        patients.removeIf(patient -> email.equals(patient.getEmail()));
    }

    public void editPatientByEmail(String email, Patient newPatient) {
        Patient patient = findPatientByEmail(email).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        patient.setIdCardNo(newPatient.getIdCardNo());
        patient.setBirthday(newPatient.getBirthday());
        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
    }

}

package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
// klasa bean service do obsługi logiki biznesowej, glownie przekierowuje do metod z PatientRepository, które działają na zasobach
@RequiredArgsConstructor // adnotacja do utworzenia konstrukora, potrzebna do wstrzykniecia zaleznosci
public class PatientService {
    private final PatientRepository patientRepository; // pole finalne ktore pobierze @ReqArgConst, ktore jest beanem i zostanie wstrzykniete w ta klase

    public void editPatientByEmail(String email, Patient newPatient) {
        PatientValidator.validateNullField(newPatient);
        Patient patient = patientRepository.findPatientByEmail(email).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        patientRepository.editPatientData(patient, newPatient);
    }

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Optional<Patient> getPatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.findPatientByEmail(email);
        PatientValidator.validatePatient(patient);
        return patient;
    }

    public void addPatient(Patient patient) {
        PatientValidator.validateEmailDuplicate(patient.getEmail(), patientRepository);
        PatientValidator.validateEmailNotNull(patient);
        patientRepository.addPatient(patient);
    }

    public void deletePatient(String email) {
        Optional<Patient> patient = patientRepository.findPatientByEmail(email);
        patient.ifPresent(PatientValidator::validateEmailNotNull);
        patientRepository.deletePatient(email);
    }

    public void editPasswordByEmail(String email, String newPassword) {
        Patient patient = patientRepository.findPatientByEmail(email).orElseThrow(() -> new IllegalArgumentException("Patient with entered email does not exist"));
        patientRepository.editPassword(patient, newPassword);
    }
}

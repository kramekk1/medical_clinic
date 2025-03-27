package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// klasa bean service do obsługi logiki biznesowej, glownie przekierowuje do metod z PatientRepository, które działają na zasobach
@RequiredArgsConstructor // adnotacja do utworzenia konstrukora, potrzebna do wstrzykniecia zaleznosci
public class PatientService {
    private final PatientRepository patientRepository; // pole finalne ktore pobierze @ReqArgConst, ktore jest beanem i zostanie wstrzykniete w ta klase

    public void editPatientByEmail(String email, Patient newPatient) {
        PatientValidator.validateNullField(newPatient);
        Patient patient = patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
        patientRepository.editPatientData(patient, newPatient);
    }

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
    }

    public void addPatient(Patient patient) {
        PatientValidator.validateNullField(patient);
        PatientValidator.validateEmailDuplicate(patient.getEmail(), patientRepository);
        patientRepository.addPatient(patient);
    }

    public void deletePatient(String email) {
        patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
        patientRepository.deletePatient(email);
    }

    public void editPasswordByEmail(String email, String newPassword) {
        Patient patient = patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
        patientRepository.editPassword(patient, newPassword);
    }
}

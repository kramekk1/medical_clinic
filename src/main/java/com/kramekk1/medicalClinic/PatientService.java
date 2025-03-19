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
        PatientValidator.validateEmailDuplicate(newPatient.getEmail(), patientRepository);
        PatientValidator.validateNullField(newPatient);
        patientRepository.editPatientByEmail(email, newPatient);
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

}

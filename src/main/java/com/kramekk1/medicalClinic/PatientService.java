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

    public void editPatientByEmail(String email, EditPatientCommand patient) {
        Patient newPatientToEntity = PatientMapper.convertToEntity(patient);
        PatientValidator.validateNullField(newPatientToEntity);
        Patient foundPatient = patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
        patientRepository.editPatientData(foundPatient, newPatientToEntity);
    }

    public List<PatientDTO> getPatients() {
        return patientRepository.getPatients()
                .stream()
                .map(PatientMapper::convertToDTO)
                .toList();
    }

    public PatientDTO getPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email)
                .map(PatientMapper::convertToDTO)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
    }

    public PatientDTO addPatient(CreatePatientCommand patient) {
        Patient patientToEntity = PatientMapper.convertToEntity(patient);
        PatientValidator.validateNullField(patientToEntity);
        PatientValidator.validateEmailDuplicate(patient.getEmail(), patientRepository);
        patientRepository.addPatient(patientToEntity);
        return PatientMapper.convertToDTO(patient);
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

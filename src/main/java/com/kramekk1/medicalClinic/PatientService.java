package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// klasa bean service do obsługi logiki biznesowej, glownie przekierowuje do metod z PatientRepository, które działają na zasobach
@RequiredArgsConstructor // adnotacja do utworzenia konstrukora, potrzebna do wstrzykniecia zaleznosci
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapperMapStruct patientMapper;

    public PatientDTO editPatientByEmail(String email, EditPatientCommand patient) {
        Patient deliveredPatientToEntity = patientMapper.toEntity(patient);
        PatientValidator.validateNullField(deliveredPatientToEntity);
        Patient foundPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));

        foundPatient.update(deliveredPatientToEntity);

        return patientMapper.toDTO(patientRepository.save(foundPatient));
    }

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDTO)
                .toList();
    }

    public PatientDTO getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
    }

    public PatientDTO addPatient(CreatePatientCommand patient) {
        Patient patientToEntity = patientMapper.toEntity(patient);
        PatientValidator.validateNullField(patientToEntity);
        PatientValidator.validateEmailDuplicate(patient.getEmail(), patientRepository);
        patientRepository.save(patientToEntity);
        return patientMapper.toDTO(patient);
    }

    public void deletePatient(String email) {
        Patient foundPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));

        patientRepository.delete(foundPatient);
    }

    public void editPasswordByEmail(String email, String newPassword) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));

        patient.updatePassword(newPassword);
        patientRepository.save(patient);
    }

    public PatientDTO register(RegisterPatientCommand command) {
        Patient patientEntity = patientMapper.toEntity(command);
        PatientValidator.validateNullField(patientEntity);
        PatientValidator.validateEmailDuplicate(command.getEmail(), patientRepository);

        patientEntity.register(command);
        return patientMapper.toDTO(patientRepository.save(patientEntity));
    }
}

package com.kramekk1.medicalClinic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
// klasa bean service do obsługi logiki biznesowej, glownie przekierowuje do metod z PatientRepository, które działają na zasobach
@RequiredArgsConstructor // adnotacja do utworzenia konstrukora, potrzebna do wstrzykniecia zaleznosci
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapperMapStruct patientMapper;

    @Transactional
    public PatientDTO editPatientByEmail(String email, EditPatientCommand patient) {
        Patient deliveredPatientToEntity = patientMapper.toEntity(patient);
        PatientValidator.validateNullField(deliveredPatientToEntity);
        Patient foundPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));

        foundPatient.update(deliveredPatientToEntity);

        return patientMapper.toDTO(patientRepository.save(foundPatient));
    }

    public Page<PatientDTO> getAll(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patientMapper::toDTO);
    }

    public PatientDTO getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void deleteByEmail(String email) {
        Patient foundPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));

        patientRepository.delete(foundPatient);
    }

    @Transactional
    public void editPasswordByEmail(String email, String newPassword) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found", HttpStatus.NOT_FOUND));

        patient.updatePassword(newPassword);
        patientRepository.save(patient);
    }

    @Transactional
    public PatientDTO register(RegisterPatientCommand command) {
        Patient patientEntity = patientMapper.toEntity(command);
        PatientValidator.validateNullField(patientEntity);
        PatientValidator.validateEmailDuplicate(command.getEmail(), patientRepository);

        patientEntity.register(command);
        return patientMapper.toDTO(patientRepository.save(patientEntity));
    }
}

package com.kramekk1.medicalClinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    PatientRepository patientRepository;
    PatientMapperMapStruct patientMapper;
    PatientService patientService;

    @BeforeEach
    void setup() {
        this.patientMapper = Mappers.getMapper(PatientMapperMapStruct.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void getPatients_allExist_patientDtoListReturned() {
        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now(), new User(), List.of());
        Patient patient1 = new Patient(2L, "email1", "idCardNo1", "firstname1", "lastname1", "phoneNumber1", LocalDate.now(), new User(), List.of());

        Pageable pageable = PageRequest.of(0, 10);
        Page<Patient> page = new PageImpl<>(List.of(patient, patient1));

        when(patientRepository.findAll(pageable)).thenReturn(page);

        Page<PatientDTO> result = patientService.getAll(pageable);

        Assertions.assertAll(
                () -> assertEquals("email", result.getContent().get(0).getEmail()),
                () -> assertEquals("firstname", result.getContent().get(0).getFirstName()),
                () -> assertEquals("firstname1", result.getContent().get(1).getFirstName()),
                () -> assertEquals("email1", result.getContent().get(1).getEmail()),
                () -> assertEquals(2, page.getTotalElements())
        );
    }

    @Test
    void getPatientByEmail_patientExist_patientDtoReturned() {
        String email = "email";
        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now(), new User(), List.of());
        when(patientRepository.findByEmail(any())).thenReturn(Optional.of(patient));

        PatientDTO result = patientService.getPatientByEmail(email);

        Assertions.assertAll(
                () -> assertEquals(email, result.getEmail()),
                () -> assertEquals("firstname", result.getFirstName()),
                () -> assertEquals("lastname", result.getLastName())
        );
    }

    @Test
    void register_registerCommandExist_patientDtoReturned() {
        RegisterPatientCommand command = new RegisterPatientCommand("username", "password", "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now());
        Patient patient = patientMapper.toEntity(command);
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.register(command);

        Assertions.assertAll(
                () -> assertEquals("firstname", result.getFirstName()),
                () -> assertEquals("lastname", result.getLastName()),
                () -> assertEquals("email", result.getEmail())
        );
    }

    @Test
    void editPasswordByEmail_patientExist_patientDataUpdated() {
        String email = "email";
        String newPassword = "new";
        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now(), new User(), List.of());
        when(patientRepository.findByEmail(any())).thenReturn(Optional.of(patient));

        patientService.editPasswordByEmail(email, newPassword);

        Assertions.assertEquals("new", patient.getUser().getPassword());
    }

    @Test
    void deleteByEmail_patientExist_patientDeleted() {
        String email = "email";
        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now(), new User(), List.of());
        when(patientRepository.findByEmail(any())).thenReturn(Optional.of(patient));

        patientService.deleteByEmail(email);

        verify(patientRepository).delete(patient);
    }

    @Test
    void editPatientByEmail_editCommandExist_patientDtoReturned() {
        String email = "email";
        EditPatientCommand command = new EditPatientCommand("username", "password", "email", "idCardNo", "firstname1", "lastname1", "phoneNumber1", LocalDate.now());
        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now(), new User(), List.of());
        when(patientRepository.findByEmail(any())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.editPatientByEmail(email, command);

        Assertions.assertAll(
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("firstname1", result.getFirstName()),
                () -> assertEquals("lastname1", result.getLastName()),
                () -> assertEquals("phoneNumber1", result.getPhoneNumber())
        );
    }

    @Test
    void editPatientByEmail_patientNotExist_exceptionThrow() {
        EditPatientCommand command = new EditPatientCommand("username", "password", "email", "idCardNo", "firstname1", "lastname1", "phoneNumber1", LocalDate.now());
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.editPatientByEmail("emailToEdit", command));
        assertEquals("Patient with email emailToEdit not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void getPatientByEmail_patientNotExist_exceptionThrow() {
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.getPatientByEmail("testEmail"));
        assertEquals("Patient with email testEmail not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void deleteByEmail_patientNotExist_exceptionThrow() {
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.deleteByEmail("testEmail"));
        assertEquals("Patient with email testEmail not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void editPasswordByEmail_patientNotExist_exceptionThrow() {
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.editPasswordByEmail("testEmail", "newPassword"));
        assertEquals("Patient with email testEmail not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

}

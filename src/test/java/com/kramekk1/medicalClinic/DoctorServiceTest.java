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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {

    DoctorService doctorService;
    DoctorRepository doctorRepository;
    InstitutionRepository institutionRepository;
    DoctorMapper doctorMapper;

    @BeforeEach
    void setup() {
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.doctorService = new DoctorService(doctorRepository, institutionRepository, doctorMapper);
    }

    @Test
    void getAll_allExist_doctorDtoListReturned() {
        //given
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        Doctor doctor1 = new Doctor(2L, "email", "password", "firstname", "surname", SpecializationType.GASTRO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());

        Pageable pageable = PageRequest.of(0, 10);
        Page<Doctor> page = new PageImpl<>(List.of(doctor, doctor1));

        when(doctorRepository.findAll(pageable)).thenReturn(page);

        //when
        Page<DoctorDTO> result = doctorService.getAll(pageable);

        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.getContent().get(0).getId()),
                () -> assertEquals("email", result.getContent().get(0).getEmail()),
                () -> assertEquals("firstname", result.getContent().get(0).getFirstname()),
                () -> assertEquals("surname", result.getContent().get(0).getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.getContent().get(0).getSpecializationType()),
                () -> assertEquals(new ArrayList<>(), result.getContent().get(0).getInstitutions()),

                () -> assertEquals(2L, result.getContent().get(1).getId()),
                () -> assertEquals("email", result.getContent().get(1).getEmail()),
                () -> assertEquals("firstname", result.getContent().get(1).getFirstname()),
                () -> assertEquals("surname", result.getContent().get(1).getSurname()),
                () -> assertEquals(SpecializationType.GASTRO, result.getContent().get(1).getSpecializationType()),
                () -> assertEquals(new ArrayList<>(), result.getContent().get(1).getInstitutions())
        );
    }

    @Test
    void getById_doctorExist_doctorDtoReturned() {
        //given
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));

        //when
        DoctorDTO result = doctorService.getById(id);

        //then
        Assertions.assertAll(
                () -> assertEquals(id, result.getId()),
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("firstname", result.getFirstname()),
                () -> assertEquals("surname", result.getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.getSpecializationType()),
                () -> assertEquals(new ArrayList<>(), result.getInstitutions())
        );
    }

    @Test
    void create_createCommandExist_doctorDtoReturned() {
        //given
        CreateDoctorCommand command = new CreateDoctorCommand("email", "password", "firstname", "surname", SpecializationType.CARDIO, "123456789");
        Doctor doctor = doctorMapper.toEntity(command);
        Long id = doctor.getId();

        when(doctorRepository.save(any())).thenReturn(doctor);

        //when
        DoctorDTO result = doctorService.create(command);

        //then
        Assertions.assertAll(
                () -> assertEquals(id, result.getId()),
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("firstname", result.getFirstname()),
                () -> assertEquals("surname", result.getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.getSpecializationType()),
                () -> assertEquals(new ArrayList<>(), result.getInstitutions())
        );

    }

    @Test
    void update_updateCommandExist_doctorDtoReturned() {
        //given
        UpdateDoctorCommand command = new UpdateDoctorCommand("email", "password", "firstname", "surname", SpecializationType.CARDIO, "123456789", new ArrayList<>());
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        Institution institution = new Institution(1L, "name", "city", "address", "postalCode", "street", "buildingNumber", new ArrayList<>());
        Institution institution1 = new Institution(2L, "name", "city", "address", "postalCode", "street", "buildingNumber", new ArrayList<>());

        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));

        when(institutionRepository.findAllById(any())).thenReturn(List.of(institution, institution1));
        when(doctorRepository.save(any())).thenReturn(doctor);

        //when
        DoctorDTO result = doctorService.update(command, id);

        //then
        Assertions.assertAll(
                () -> assertEquals(id, result.getId()),
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("firstname", result.getFirstname()),
                () -> assertEquals("surname", result.getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.getSpecializationType()),
                () -> assertEquals(2, result.getInstitutions().size()),
                () -> assertEquals(1L, result.getInstitutions().get(0).getId()),
                () -> assertEquals(2L, result.getInstitutions().get(1).getId())
        );
    }

    @Test
    void addInstitutionToDoctorById_BothExist_DoctorDtoWithInstitutionDtoReturned() {
        Long idDoc = 1L;
        Long idInst = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        Institution institution = new Institution(1L, "name", "city", "address", "postalCode", "street", "buildingNumber", new ArrayList<>());

        when(doctorRepository.findById(idDoc)).thenReturn(Optional.of(doctor));
        when(institutionRepository.findById(idInst)).thenReturn(Optional.of(institution));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        DoctorDTO result = doctorService.addInstitutionToDoctorById(idDoc, idInst);

        Assertions.assertAll(
                () -> assertEquals(idDoc, result.getId()),
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("firstname", result.getFirstname()),
                () -> assertEquals("surname", result.getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.getSpecializationType()),
                () -> assertEquals("address", result.getInstitutions().get(0).getAddress()),
                () -> assertEquals(1, result.getInstitutions().size())
        );
    }

    @Test
    void deleteById_doctorExist_doctorDeleted() {
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, List.of(), "123456789", new DoctorVisitStatsEntity(), List.of());

        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));

        doctorService.deleteById(id);

        verify(doctorRepository).delete(doctor);
    }

    @Test
    void getById_doctorNotExist_exceptionThrow() {
        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        DoctorNotFoundException exception = assertThrows(DoctorNotFoundException.class, () -> doctorService.getById(1L));

        assertEquals("Doctor with this ID " + 1L + " not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void update_doctorNotExist_exceptionThrow() {
        UpdateDoctorCommand command = new UpdateDoctorCommand("email", "password", "firstname", "surname", SpecializationType.CARDIO, "123456789", new ArrayList<>());
        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        DoctorNotFoundException exception = assertThrows(DoctorNotFoundException.class, () -> doctorService.update(command, 1L));
        assertEquals("Doctor with this ID " + 1L + " not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void deleteById_doctorNotExist_exceptionThrow() {
        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        DoctorNotFoundException exception = assertThrows(DoctorNotFoundException.class, () -> doctorService.deleteById(1L));
        assertEquals("Doctor with this ID " + 1L + " not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void addInstitutionToDoctorById_doctorNotExist_exceptionThrow() {
        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        DoctorNotFoundException docException = assertThrows(DoctorNotFoundException.class, () -> doctorService.addInstitutionToDoctorById(1L, 1L));
        assertEquals("Doctor with this ID " + 1L + " not found", docException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, docException.getHttpStatus());
    }

    @Test
    void addInstitutionToDoctorById_institutionNotExist_exceptionThrow() {
        when(doctorRepository.findById(any())).thenReturn(Optional.of(new Doctor()));
        when(institutionRepository.findById(any())).thenReturn(Optional.empty());

        InstitutionNotFoundException instException = assertThrows(InstitutionNotFoundException.class, () -> doctorService.addInstitutionToDoctorById(1L, 1L));
        assertEquals("Institution with this ID " + 1L + " not found", instException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, instException.getHttpStatus());
    }
}

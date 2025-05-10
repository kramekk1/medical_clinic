package com.kramekk1.medicalClinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), new ArrayList<>());
        Doctor doctor1 = new Doctor(2L, "email", "password", "firstname", "surname", SpecializationType.GASTRO, new ArrayList<>(), new ArrayList<>());

        when(doctorRepository.findAll()).thenReturn(List.of(doctor, doctor1));

        //when
        List<DoctorDTO> result = doctorService.getAll();

        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals("email", result.get(0).getEmail()),
                () -> assertEquals("firstname", result.get(0).getFirstname()),
                () -> assertEquals("surname", result.get(0).getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.get(0).getSpecializationType()),
                () -> assertEquals(new ArrayList<>(), result.get(0).getInstitutions()),

                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals("email", result.get(1).getEmail()),
                () -> assertEquals("firstname", result.get(1).getFirstname()),
                () -> assertEquals("surname", result.get(1).getSurname()),
                () -> assertEquals(SpecializationType.GASTRO, result.get(1).getSpecializationType()),
                () -> assertEquals(new ArrayList<>(), result.get(1).getInstitutions())
        );
    }

    @Test
    void getById_doctorExist_doctorDtoReturned() {
        //given
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), new ArrayList<>());

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
        CreateDoctorCommand command = new CreateDoctorCommand("email", "password", "firstname", "surname",SpecializationType.CARDIO);
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
        UpdateDoctorCommand command = new UpdateDoctorCommand("email", "password", "firstname", "surname",SpecializationType.CARDIO, new ArrayList<>());
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), new ArrayList<>());
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
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), new ArrayList<>());
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
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, List.of(), List.of());

        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));

        doctorService.deleteById(id);

        verify(doctorRepository).deleteById(id);
    }

}

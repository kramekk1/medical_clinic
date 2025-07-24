package com.kramekk1.medicalClinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DoctorServiceTestMapperMock {

    DoctorService doctorService;
    DoctorRepository doctorRepository;
    InstitutionRepository institutionRepository;
    DoctorMapper doctorMapper;

    @BeforeEach
    void setup() {
        this.doctorMapper = Mockito.mock(DoctorMapper.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.doctorService = new DoctorService(doctorRepository, institutionRepository, doctorMapper);
    }

    @Test
    void getAll_allExist_doctorDtoListReturned() {
        //given
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        Doctor doctor1 = new Doctor(2L, "email1", "password1", "firstname1", "surname1", SpecializationType.GASTRO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());

        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());
        DoctorDTO doctorDTO1 = new DoctorDTO(2L, "email1", "firstname1", "surname1", SpecializationType.GASTRO, "123456789", List.of());

        Pageable pageable = PageRequest.of(0, 10);
        Page<Doctor> page = new PageImpl<>(List.of(doctor, doctor1));

        when(doctorRepository.findAll(pageable)).thenReturn(page);
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);
        when(doctorMapper.toDTO(doctor1)).thenReturn(doctorDTO1);
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
                () -> assertEquals("email1", result.getContent().get(1).getEmail()),
                () -> assertEquals("firstname1", result.getContent().get(1).getFirstname()),
                () -> assertEquals("surname1", result.getContent().get(1).getSurname()),
                () -> assertEquals(SpecializationType.GASTRO, result.getContent().get(1).getSpecializationType()),
                () -> assertEquals(new ArrayList<>(), result.getContent().get(1).getInstitutions())
        );
    }

    @Test
    void getById_doctorExist_doctorDtoReturned() {
        //given
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());

        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);
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
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());

        when(doctorMapper.toEntity(command)).thenReturn(doctor);
        when(doctorRepository.save(any())).thenReturn(doctor);
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);
        //when
        DoctorDTO result = doctorService.create(command);

        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.getId()),
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

        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());
        Institution institution = new Institution(1L, "name", "city", "address", "postalCode", "street", "buildingNumber", new ArrayList<>());
        Institution institution1 = new Institution(2L, "name", "city", "address", "postalCode", "street", "buildingNumber", new ArrayList<>());

        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));

        when(institutionRepository.findAllById(any())).thenReturn(List.of(institution, institution1));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);
        when(doctorRepository.save(any())).thenReturn(doctor);

        //when
        DoctorDTO result = doctorService.update(command, 1L);

        //then
        Assertions.assertAll(
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("firstname", result.getFirstname()),
                () -> assertEquals("surname", result.getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.getSpecializationType())
        );
    }

    @Test
    void addInstitutionToDoctorById_BothExist_DoctorDtoWithInstitutionDtoReturned() {
        Long idDoc = 1L;
        Long idInst = 1L;
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());
        Institution institution = new Institution(1L, "name", "city", "address", "postalCode", "street", "buildingNumber", new ArrayList<>());

        when(doctorRepository.findById(idDoc)).thenReturn(Optional.of(doctor));
        when(institutionRepository.findById(idInst)).thenReturn(Optional.of(institution));
        when(doctorMapper.toDTO(doctor)).thenReturn(doctorDTO);
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        DoctorDTO result = doctorService.addInstitutionToDoctorById(idDoc, idInst);

        Assertions.assertAll(
                () -> assertEquals(idDoc, result.getId()),
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("firstname", result.getFirstname()),
                () -> assertEquals("surname", result.getSurname()),
                () -> assertEquals(SpecializationType.CARDIO, result.getSpecializationType())
        );
    }
}

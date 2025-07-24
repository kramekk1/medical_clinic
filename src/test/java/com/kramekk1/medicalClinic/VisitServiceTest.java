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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VisitServiceTest {

    VisitRepository visitRepository;
    VisitMapper visitMapper;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    VisitService visitService;

    @BeforeEach
    void setup() {
        this.visitRepository = Mockito.mock(VisitRepository.class);
        this.visitMapper = Mappers.getMapper(VisitMapper.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.visitService = new VisitService(visitRepository, visitMapper, doctorRepository, patientRepository);
    }

    @Test
    void create_createCommandExist_visitDtoReturned() {
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(23, 15, 0);
        LocalTime endTime = LocalTime.of(23, 30,0);

        CreateVisitCommand command = new CreateVisitCommand(date.atTime(startTime), date.atTime(endTime), 1L);
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), "123456789", new DoctorVisitStatsEntity(), new ArrayList<>());
        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));

        Visit visit = new Visit(1L, date.atTime(startTime), date.atTime(endTime), VisitStatus.COMPLETED, doctor, new Patient());
        when(visitRepository.save(any())).thenReturn(visit);

        VisitDTO result = visitService.create(command);

        Assertions.assertAll(
                () -> assertEquals(1L, result.getDoctorId()),
                () -> assertEquals(23, result.getStartAt().getHour()),
                () -> assertEquals(15, result.getStartAt().getMinute()),
                () -> assertEquals(30, result.getEndTime().getMinute())
        );
    }

    @Test
    void assignPatient_patientAndVisitExist_visitDtoReturned() {
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(22, 15, 0);
        LocalTime endTime = LocalTime.of(23, 30,0);

        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", date, new User(), List.of());
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        Visit visit = new Visit(1L, date.atTime(startTime), date.atTime(endTime), VisitStatus.COMPLETED, new Doctor(), null);
        when(visitRepository.findById(visit.getId())).thenReturn(Optional.of(visit));
        when(visitRepository.save(any())).thenReturn(visit);

        VisitDTO result = visitService.assignPatient(visit.getId(), patient.getId());

        Assertions.assertAll(
                () -> assertEquals(1L, result.getPatientId())
        );
    }

    @Test
    void getAll_allExist_visitDtoListReturned() {
        Visit visit = new Visit(1L, LocalDateTime.now(), LocalDateTime.now(), VisitStatus.COMPLETED, new Doctor(), new Patient());
        Visit visit1 = new Visit(2L, LocalDateTime.now(), LocalDateTime.now(), VisitStatus.COMPLETED, new Doctor(), new Patient());

        Pageable pageable = PageRequest.of(0,10);
        Page<Visit> page = new PageImpl<>(List.of(visit, visit1));

        when(visitRepository.findAll(pageable)).thenReturn(page);

        Page<VisitDTO> result = visitService.getAll(pageable);

        Assertions.assertAll(
                () -> assertEquals(2, result.getTotalElements())
        );
    }

    @Test
    void getByPatientId_patientWithVisitsExist_visitDtoListReturned() {
        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now(), new User(), List.of());
        Visit visit = new Visit(1L, LocalDateTime.now(), LocalDateTime.now(), VisitStatus.COMPLETED, new Doctor(), patient);
        Visit visit1 = new Visit(2L, LocalDateTime.now(), LocalDateTime.now(), VisitStatus.COMPLETED, new Doctor(), patient);

        Pageable pageable = PageRequest.of(0,10);
        Page<Visit> page = new PageImpl<>(List.of(visit, visit1));

        when(visitRepository.findAllByPatient_Id(any(), any(Pageable.class))).thenReturn(page);

        Page<VisitDTO> result = visitService.getByPatientId(patient.getId(), pageable);

        Assertions.assertAll(
                () -> assertEquals(2, result.getTotalElements()),
                () -> assertEquals(1L, result.getContent().get(0).getPatientId()),
                () -> assertEquals(1L, result.getContent().get(1).getPatientId())
        );
    }

    @Test
    void deleteById_visitIdExist_visitDeleted() {
        Long id = 1L;

        visitService.deleteById(id);

        verify(visitRepository).deleteById(id);
    }

    @Test
    void create_doctorNotExist_exceptionThrow() {
        CreateVisitCommand command = new CreateVisitCommand(LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        DoctorNotFoundException exception = assertThrows(DoctorNotFoundException.class, () -> visitService.create(command));
        assertEquals("Doctor with this ID " + 1L + " not exist", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void assignPatient_patientNotExist_exceptionThrow() {
        when(patientRepository.findById(any())).thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> visitService.assignPatient(1L, 1L));
        assertEquals("Patient with this ID " + 1L + " not exist", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void assignPatient_visitNotExist_exceptionThrow() {
        when(patientRepository.findById(any())).thenReturn(Optional.of(new Patient()));
        when(visitRepository.findById(any())).thenReturn(Optional.empty());

        VisitNotFoundException exception = assertThrows(VisitNotFoundException.class, () -> visitService.assignPatient(1L, 1L));
        assertEquals("Visit with this ID " + 1L + " not exist", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}

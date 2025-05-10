package com.kramekk1.medicalClinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Doctor doctor = new Doctor(1L, "email", "password", "firstname", "surname", SpecializationType.CARDIO, new ArrayList<>(), new ArrayList<>());
        when(doctorRepository.findById(any())).thenReturn(Optional.of(doctor));

        Visit visit = new Visit(1L, date.atTime(startTime), date.atTime(endTime), doctor, new Patient());
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
        Visit visit = new Visit(1L, date.atTime(startTime), date.atTime(endTime), new Doctor(), null);
        when(visitRepository.findById(visit.getId())).thenReturn(Optional.of(visit));
        when(visitRepository.save(any())).thenReturn(visit);

        VisitDTO result = visitService.assignPatient(visit.getId(), patient.getId());

        Assertions.assertAll(
                () -> assertEquals(1L, result.getPatientId())
        );
    }

    @Test
    void getAll_allExist_visitDtoListReturned() {
        Visit visit = new Visit(1L, LocalDateTime.now(), LocalDateTime.now(), new Doctor(), new Patient());
        Visit visit1 = new Visit(2L, LocalDateTime.now(), LocalDateTime.now(), new Doctor(), new Patient());

        when(visitRepository.findAll()).thenReturn(List.of(visit, visit1));

        List<VisitDTO> result = visitService.getAll();

        Assertions.assertAll(
                () -> assertEquals(2, result.size())
        );
    }

    @Test
    void getByPatientId_patientWithVisitsExist_visitDtoListReturned() {
        Patient patient = new Patient(1L, "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now(), new User(), List.of());
        Visit visit = new Visit(1L, LocalDateTime.now(), LocalDateTime.now(), new Doctor(), patient);
        Visit visit1 = new Visit(2L, LocalDateTime.now(), LocalDateTime.now(), new Doctor(), patient);

        when(visitRepository.findAllByPatient_Id(any())).thenReturn(List.of(visit, visit1));

        List<VisitDTO> result = visitService.getByPatientId(patient.getId());

        Assertions.assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(1L, result.get(0).getPatientId()),
                () -> assertEquals(1L, result.get(1).getPatientId())
        );
    }

    @Test
    void deleteById_visitIdExist_visitDeleted() {
        Long id = 1L;

        visitService.deleteById(id);

        verify(visitRepository).deleteById(id);
    }
}

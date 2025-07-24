package com.kramekk1.medicalClinic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public VisitDTO create(CreateVisitCommand command) {
        Doctor doctor = doctorRepository.findById(command.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + command.getDoctorId() + " not exist", HttpStatus.NOT_FOUND));

        VisitValidator.validateVisitCreation(command, visitRepository);

//        doctor.updateTotalVisitCreatedStats();
        Visit visit = visitMapper.toEntity(command);
        visit.setDoctor(doctor);
        visit.setStatus(VisitStatus.FREE);

        return visitMapper.toDTO(visitRepository.save(visit));
    }

    public VisitDTO assignPatient(Long visitId, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with this ID " + patientId + " not exist", HttpStatus.NOT_FOUND));

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit with this ID " + visitId + " not exist", HttpStatus.NOT_FOUND));

        VisitValidator.validateVisitBooking(visit);

        visit.setPatient(patient);
        visit.setStatus(VisitStatus.SCHEDULED);

        return visitMapper.toDTO(visitRepository.save(visit));
    }

    public Page<VisitDTO> getAll(Pageable pageable) {
        return visitRepository.findAll(pageable)
                .map(visitMapper::toDTO);
    }

    public Page<VisitDTO> getByPatientId(Long patientId, Pageable pageable) {
        return visitRepository.findAllByPatient_Id(patientId, pageable)
                .map(visitMapper::toDTO);
    }

    public Page<VisitDTO> getByDoctorId(Long doctorId, Pageable pageable) {
        return visitRepository.findAllByDoctor_Id(doctorId, pageable)
                .map(visitMapper::toDTO);
    }

    @Transactional
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }

    public List<VisitDTO> getAvailableVisitsBySpecializationAndDate(SpecializationAndDateFilterCommand filter) {
        LocalDateTime startOfDay = filter.getDate().atStartOfDay();
        LocalDateTime endOfDay = filter.getDate().atTime(LocalTime.MAX);
        SpecializationType specializationType = filter.getSpecializationType();

        return visitRepository.findAllByStartAtBetweenAndStatusAndDoctor_SpecializationType(startOfDay, endOfDay, VisitStatus.FREE, specializationType)
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }

    public List<VisitDTO> getAvailableVisitsByDate(DateFilterCommand filter) {
        LocalDateTime startOfDay = filter.getDate().atStartOfDay();
        LocalDateTime endOfDay = filter.getDate().atTime(LocalTime.MAX);

        return visitRepository.findAllByStartAtBetweenAndStatus(startOfDay, endOfDay, VisitStatus.FREE)
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }

    public VisitDTO cancelVisit(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit with this ID " + id + " not exist", HttpStatus.NOT_FOUND));

        visit.setStatus(VisitStatus.CANCELED);
        return visitMapper.toDTO(visitRepository.save(visit));
    }

    public void refreshVisitsStatus() {
        List<Visit> visitsToUpdate = visitRepository.findByEndTimeBeforeAndStatus(
                LocalDateTime.now(), VisitStatus.SCHEDULED);

        visitsToUpdate.forEach(visit -> visit.setStatus(VisitStatus.COMPLETED));
        visitRepository.saveAll(visitsToUpdate);
    }

    public List<VisitDTO> getByDoctorIdAndFreeStatus(Long id) {
        return visitRepository.findAllByStatusAndDoctor_Id(VisitStatus.FREE, id)
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }
}

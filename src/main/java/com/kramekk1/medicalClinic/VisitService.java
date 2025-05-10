package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public VisitDTO create(CreateVisitCommand command) {
        Doctor doctor = doctorRepository.findById(command.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + command.getDoctorId() + " not exist", HttpStatus.NOT_FOUND));

        VisitValidator.validateVisitCreation(command, visitRepository);

        Visit visit = visitMapper.toEntity(command);
        visit.setDoctor(doctor);

        return visitMapper.toDTO(visitRepository.save(visit));
    }

    public VisitDTO assignPatient(Long visitId, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with this ID " + patientId + " not exist", HttpStatus.NOT_FOUND));

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit with this ID " + visitId + " not exist", HttpStatus.NOT_FOUND));

        VisitValidator.validateVisitBooking(visit);

        visit.setPatient(patient);

        return visitMapper.toDTO(visitRepository.save(visit));
    }

    public List<VisitDTO> getAll() {
        return visitRepository.findAll()
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }

    public List<VisitDTO> getByPatientId(Long patientId) {
        return visitRepository.findAllByPatient_Id(patientId)
                .stream()
                .map(visitMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }
}

package com.kramekk1.medicalClinic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final InstitutionRepository institutionRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    public DoctorDTO create(CreateDoctorCommand command) {
        DoctorValidator.validateNullField(command);
        DoctorValidator.validateEmailDuplicate(command.getEmail(), doctorRepository);
        Doctor doctor = doctorMapper.toEntity(command);
        doctorRepository.save(doctor);
        return doctorMapper.toDTO(doctor);
    }

    public Page<DoctorDTO> getAll(Pageable pageable) {
        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toDTO);
    }

    public DoctorDTO getById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + id + " not found", HttpStatus.NOT_FOUND));
        return doctorMapper.toDTO(doctor);
    }

    @Transactional
    public DoctorDTO update(UpdateDoctorCommand command, Long id) {
        DoctorValidator.validateNullField(command);
        Doctor foundedDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + id + " not found", HttpStatus.NOT_FOUND));

        foundedDoctor.setInstitutions(institutionRepository.findAllById(command.getInstitutionIds()));

        foundedDoctor.update(command);
        return doctorMapper.toDTO(doctorRepository.save(foundedDoctor));
    }

    @Transactional
    public void deleteById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + id + " not found", HttpStatus.NOT_FOUND));

        doctorRepository.delete(doctor);
    }

    @Transactional
    public DoctorDTO addInstitutionToDoctorById(Long doctorId, Long institutionId) {
        Doctor foundedDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + doctorId + " not found", HttpStatus.NOT_FOUND));

        Institution foundedInstitution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with this ID " + institutionId + " not found", HttpStatus.NOT_FOUND));

        foundedDoctor.addInstitutionToDoctor(foundedInstitution);

        return doctorMapper.toDTO(doctorRepository.save(foundedDoctor));
    }

    public List<DoctorDTO> getBySpecialization(SpecializationType specializationType) {
        return doctorRepository.findAllBySpecializationType(specializationType)
                .stream()
                .map(doctorMapper::toDTO)
                .toList();
    }
}

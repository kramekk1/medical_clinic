package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorDTO create(CreateDoctorCommand command) {
        DoctorValidator.validateNullField(command);
        DoctorValidator.validateEmailDuplicate(command.getEmail(), doctorRepository);
        Doctor doctor = doctorMapper.toEntity(command);
        doctorRepository.save(doctor);
        return doctorMapper.toDTO(doctor);
    }

    public List<DoctorDTO> getAll() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDTO)
                .toList();
    }

    public DoctorDTO getById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + id +" not found", HttpStatus.NOT_FOUND));
        return doctorMapper.toDTO(doctor);
    }

    public DoctorDTO update(UpdateDoctorCommand command, Long id) {
        DoctorValidator.validateNullField(command);
        Doctor foundedDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + id +" not found", HttpStatus.NOT_FOUND));

        foundedDoctor.update(command);
        return doctorMapper.toDTO(doctorRepository.save(foundedDoctor));
    }

    public void deleteById(Long id) {
        doctorRepository.findById(id)
                        .orElseThrow(() -> new DoctorNotFoundException("Doctor with this ID " + id +" not found", HttpStatus.NOT_FOUND));

        doctorRepository.deleteById(id);
    }
}

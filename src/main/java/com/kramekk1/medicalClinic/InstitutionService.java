package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public List<InstitutionDTO> getAll() {
        return institutionRepository.findAll().stream()
                .map(institutionMapper::toDTO)
                .toList();
    }

    public void deleteById(Long id) {
        institutionRepository.findById(id)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with this name not exist", HttpStatus.NOT_FOUND));
        institutionRepository.deleteById(id);
    }

    public InstitutionDTO create(CreateInstitutionCommand command) {
        Institution institutionToEntity = institutionMapper.toEntity(command);
        InstitutionValidator.validateInstitutionFields(institutionToEntity);
        InstitutionValidator.validateInstitutionNameDuplicate(institutionToEntity, institutionRepository);

        return institutionMapper.toDTO(institutionRepository.save(institutionToEntity));
    }
}

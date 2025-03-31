package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapperMapStruct institutionMapper;

    public List<InstitutionDTO> getAll() {
        return institutionRepository.getAll().stream()
                .map(institutionMapper::toDTO)
                .toList();
    }

    public void delete(String institutionName) {
        institutionRepository.findByName(institutionName)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with this name not exist", HttpStatus.NOT_FOUND));
        institutionRepository.delete(institutionName);
    }

    public InstitutionDTO add(CreateInstitutionCommand institution) {
        Institution institutionToEntity = institutionMapper.toEntity(institution);
        InstitutionValidator.validateInstitutionFields(institutionToEntity);
        InstitutionValidator.validateInstitutionNameDuplicate(institutionToEntity, institutionRepository);
        institutionRepository.add(institutionToEntity);
        return institutionMapper.toDTO(institution);
    }
}

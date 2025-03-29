package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public List<InstitutionDTO> getAll() {
        return institutionRepository.getAll().stream()
                .map(InstitutionMapper::convertToDTO)
                .toList();
    }

    public void delete(String institutionName) {
        institutionRepository.findByName(institutionName)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with this name not exist", HttpStatus.NOT_FOUND));
        institutionRepository.delete(institutionName);
    }

    public InstitutionDTO add(CreateInstitutionCommand institution) {
        Institution institutionToEntity = InstitutionMapper.convertToEntity(institution);
        InstitutionValidator.validateInstitutionFields(institutionToEntity);
        InstitutionValidator.validateInstitutionNameDuplicate(institutionToEntity, institutionRepository);
        institutionRepository.add(institutionToEntity);
        return InstitutionMapper.convertToDTO(institution);
    }
}

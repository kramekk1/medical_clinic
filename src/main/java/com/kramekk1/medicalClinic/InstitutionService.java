package com.kramekk1.medicalClinic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public Page<InstitutionDTO> getAll(Pageable pageable) {
        return institutionRepository.findAll(pageable)
                .map(institutionMapper::toDTO);
    }

    @Transactional
    public void deleteById(Long id) {
        institutionRepository.findById(id)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution with this ID" + id + " not exist", HttpStatus.NOT_FOUND));
        institutionRepository.deleteById(id);
    }

    @Transactional
    public InstitutionDTO create(CreateInstitutionCommand command) {
        Institution institutionToEntity = institutionMapper.toEntity(command);
        InstitutionValidator.validateInstitutionFields(institutionToEntity);
        InstitutionValidator.validateInstitutionNameDuplicate(institutionToEntity, institutionRepository);

        return institutionMapper.toDTO(institutionRepository.save(institutionToEntity));
    }
}

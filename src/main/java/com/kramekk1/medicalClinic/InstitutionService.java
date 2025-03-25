package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public List<Institution> getAll() {
        return institutionRepository.getAll();
    }

    public void delete(String institutionName) {
        institutionRepository.findByName(institutionName).orElseThrow(() -> new IllegalArgumentException("Institution with this name not exist"));
        institutionRepository.delete(institutionName);
    }

    public void add(Institution institution) {
        InstitutionValidator.validateInstitutionFields(institution);
        InstitutionValidator.validateInstitutionNameDuplicate(institution, institutionRepository);
        institutionRepository.add(institution);
    }
}

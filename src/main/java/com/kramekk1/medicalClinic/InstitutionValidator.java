package com.kramekk1.medicalClinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstitutionValidator {

    public static void validateInstitutionFields(Institution institution) {
        if (institution.getName() == null || institution.getPostalCode() == null || institution.getAddress() == null) {
            throw new InstitutionDataFieldNullException("One or more institution data field is null", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateInstitutionNameDuplicate(Institution institution, InstitutionRepository institutionRepository) {
        if (institutionRepository.getAll().stream().anyMatch(inst -> institution.getName().equals(inst.getName()))) {
            throw new InstitutionNameDuplicateException("Institution with this name already exist", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.kramekk1.medicalClinic;

public class InstitutionValidator {

    public static void validateInstitutionFields(Institution institution) {
        if (institution.getName() == null || institution.getPostalCode() == null || institution.getAddress() == null) {
            throw new IllegalArgumentException("One or more institution data field is null");
        }
    }

    public static void validateInstitutionNameDuplicate(Institution institution, InstitutionRepository institutionRepository) {
        if (institutionRepository.getAll().stream().anyMatch(inst -> institution.getName().equals(inst.getName()))) {
            throw new IllegalArgumentException("Institution with this name already exist");
        }
    }
}

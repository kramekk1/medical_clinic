package com.kramekk1.medicalClinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstitutionMapper {

    public static InstitutionDTO convertToDTO(Institution institution) {
        return new InstitutionDTO(institution.getName(), institution.getPostalCode(), institution.getAddress());
    }

    public static InstitutionDTO convertToDTO(CreateInstitutionCommand institution) {
        return new InstitutionDTO(institution.getName(), institution.getPostalCode(), institution.getAddress());
    }

    public static Institution convertToEntity(CreateInstitutionCommand institution) {
        return new Institution(institution.getName(), institution.getPostalCode(), institution.getAddress());
    }
}

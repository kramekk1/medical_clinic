package com.kramekk1.medicalClinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    InstitutionDTO toDTO(Institution institution);

    InstitutionDTO toDTO(CreateInstitutionCommand institutionCommand);

    Institution toEntity(CreateInstitutionCommand institutionCommand);

}

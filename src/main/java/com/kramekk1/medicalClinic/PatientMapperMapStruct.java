package com.kramekk1.medicalClinic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PatientMapperMapStruct {

    @Mapping(source = ".", target = "fullname", qualifiedByName = "mapToFullName")
    PatientDTO toDTO(Patient patient);

    @Mapping(source = ".", target = "fullname", qualifiedByName = "mapToFullName")
    PatientDTO toDTO(CreatePatientCommand createPatientCommand);

    Patient toEntity(CreatePatientCommand createPatientCommand);

    Patient toEntity(EditPatientCommand editPatientCommand);

    @Named("mapToFullName")
    default String toFullName(Patient patient) {
        return patient.getFirstName() + " " + patient.getLastName();
    }

    @Named("mapToFullName")
    default String toFullName(CreatePatientCommand createPatientCommand) {
        return createPatientCommand.getFirstName() + " " + createPatientCommand.getLastName();
    }
}

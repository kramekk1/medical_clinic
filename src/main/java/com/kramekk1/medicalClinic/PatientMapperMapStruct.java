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

    @Mapping(source = ".", target = "fullname", qualifiedByName = "mapToFullName")
    PatientDTO toDTO(RegisterPatientCommand command);

    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "password", target = "user.password")
    Patient toEntity(CreatePatientCommand createPatientCommand);

    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "password", target = "user.password")
    Patient toEntity(EditPatientCommand editPatientCommand);

    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "password", target = "user.password")
    Patient toEntity(RegisterPatientCommand command);

    @Named("mapToFullName")
    default String toFullName(Patient patient) {
        return patient.getFirstName() + " " + patient.getLastName();
    }

    @Named("mapToFullName")
    default String toFullName(CreatePatientCommand createPatientCommand) {
        return createPatientCommand.getFirstName() + " " + createPatientCommand.getLastName();
    }

    @Named("mapToFullName")
    default String toFullName(RegisterPatientCommand command) {
        return command.getFirstName() + " " + command.getLastName();
    }
}

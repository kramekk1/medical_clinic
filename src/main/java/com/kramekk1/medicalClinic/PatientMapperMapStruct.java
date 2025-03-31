package com.kramekk1.medicalClinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapperMapStruct {

    PatientDTO toDTO(Patient patient);

    PatientDTO toDTO(CreatePatientCommand createPatientCommand);

    Patient toEntity(CreatePatientCommand createPatientCommand);

    Patient toEntity(EditPatientCommand editPatientCommand);
}

package com.kramekk1.medicalClinic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    VisitDTO toDTO(CreateVisitCommand command);

    Visit toEntity(CreateVisitCommand command);

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    VisitDTO toDTO(Visit visit);

}

package com.kramekk1.medicalClinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDTO(Doctor doctor);

    Doctor toEntity(CreateDoctorCommand command);

    SimpleDoctorDTO toDTO(DoctorDTO doctorDTO);

}

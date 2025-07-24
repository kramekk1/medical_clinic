package com.kramekk1.medicalClinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatisticMapper {

    DoctorVisitStatsDTO toDTO(DoctorVisitStatsEntity doctorVisitStatsEntity);
}

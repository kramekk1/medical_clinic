package com.kramekk1.medicalClinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InstitutionServiceTest {

    InstitutionRepository institutionRepository;
    InstitutionMapper institutionMapper;
    InstitutionService institutionService;

    @BeforeEach
    void setup() {
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.institutionMapper = Mappers.getMapper(InstitutionMapper.class);
        this.institutionService = new InstitutionService(institutionRepository, institutionMapper);
    }

    @Test
    void getAll_allExist_institutionDtoListReturned() {
        Institution institution = new Institution(1L, "name", "city", "address", "postalCode", "street", "buildingNumber", new ArrayList<>());
        Institution institution1 = new Institution(2L, "name1", "city1", "address1", "postalCode1", "street1", "buildingNumbe1r", new ArrayList<>());

        when(institutionRepository.findAll()).thenReturn(List.of(institution, institution1));

        List<InstitutionDTO> result = institutionService.getAll();

        Assertions.assertAll(
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals("name", result.get(0).getName()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals(2, result.size())
        );
    }

    @Test // ?
    void create_institutionCreated_institutionDtoReturned() {
        CreateInstitutionCommand command = new CreateInstitutionCommand("name", "city", "address", "postalCode", "street", "buildingNumber");
        Institution institution = institutionMapper.toEntity(command);
        //Institution institutionFromDB = new Institution(2L, "name1", "city1", "address1", "postalCode1", "street1", "buildingNumbe1r", new ArrayList<>());

       // when(institutionRepository.findAll()).thenReturn(List.of(institutionFromDB));
        when(institutionRepository.save(any())).thenReturn(institution);

        InstitutionDTO result = institutionService.create(command);

        Assertions.assertAll(
                () -> assertEquals("name", result.getName()),
                () -> assertEquals("postalCode", result.getPostalCode()),
                () -> assertEquals("street", result.getStreet())
        );
    }

    @Test
    void deleteById_institutionExist_institutionDeleted() {
        Long id = 1L;
        Institution institution = new Institution(1L, "name", "city", "address", "postalCode", "street", "buildingNumber", List.of());
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));

        institutionService.deleteById(id);

        verify(institutionRepository).deleteById(institution.getId());
    }
}

package com.kramekk1.medicalClinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.liquibase.enabled=false")
@AutoConfigureMockMvc
public class InstitutionControllerTest {

    @MockitoBean
    private InstitutionService institutionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getAll_institutionDtoListReturnedWithStatus200() throws Exception {
        InstitutionDTO institutionDTO = new InstitutionDTO(1L, "name", "postalCode", "address", "street", "buildingNumber", new ArrayList<>());
        InstitutionDTO institutionDTO1 = new InstitutionDTO(2L, "name1", "postalCode1", "address1", "street1", "buildingNumber1", new ArrayList<>());

        when(institutionService.getAll()).thenReturn(List.of(institutionDTO, institutionDTO1));

        mockMvc.perform(get("/institutions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[1].name").value("name1"));
    }

    @Test
    public void deleteById_institutionDeleted() throws Exception {
        mockMvc.perform(delete("/institutions/1"))
                .andExpect(status().isNoContent());

        verify(institutionService).deleteById(1L);
    }

    @Test
    public void create_institutionDtoReturnedWithStatus201() throws Exception {
        InstitutionDTO institutionDTO = new InstitutionDTO(1L, "name", "postalCode", "address", "street", "buildingNumber", new ArrayList<>());
        CreateInstitutionCommand command = new CreateInstitutionCommand("name", "city", "address", "postalCode", "street", "buildingNumber");

        when(institutionService.create(any())).thenReturn(institutionDTO);

        mockMvc.perform(post("/institutions")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.street").value("street"));
    }
}

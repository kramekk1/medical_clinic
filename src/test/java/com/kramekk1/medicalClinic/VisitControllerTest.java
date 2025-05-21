package com.kramekk1.medicalClinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.liquibase.enabled=false")
@AutoConfigureMockMvc
public class VisitControllerTest {

    @MockitoBean
    private VisitService visitService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getAll_visitDtoListReturnedWithStatus200() throws Exception {
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        VisitDTO visitDTO1 = new VisitDTO(LocalDateTime.now(), LocalDateTime.now(), 2L, 2L);

        when(visitService.getAll()).thenReturn(List.of(visitDTO, visitDTO1));

        mockMvc.perform(get("/visits"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorId").value(1L))
                .andExpect(jsonPath("$[1].patientId").value(2L));
    }

    @Test
    public void getByPatientId_visitDtoListReturnedWithStatus200() throws Exception {
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        VisitDTO visitDTO1 = new VisitDTO(LocalDateTime.now(), LocalDateTime.now(), 2L, 2L);

        when(visitService.getByPatientId(any())).thenReturn(List.of(visitDTO, visitDTO1));

        mockMvc.perform(get("/visits/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorId").value(1L))
                .andExpect(jsonPath("$[1].patientId").value(2L));
    }

    @Test
    public void create_visitDtoReturnedWithStatus201() throws Exception {
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        CreateVisitCommand command = new CreateVisitCommand(LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(visitService.create(any())).thenReturn(visitDTO);

        mockMvc.perform(post("/visits")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(jsonPath("$.patientId").value(1L));
    }

    @Test
    public void assignPatient_visitDtoReturnedWithStatus200() throws Exception {
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        AssignPatientCommand command = new AssignPatientCommand(1L);

        when(visitService.assignPatient(any(), any())).thenReturn(visitDTO);

        mockMvc.perform(patch("/visits/1")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(jsonPath("$.patientId").value(1L));
    }

    @Test
    public void deleteById_visitDeleted() throws Exception {
        mockMvc.perform(delete("/visits/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(visitService).deleteById(any());
    }
}

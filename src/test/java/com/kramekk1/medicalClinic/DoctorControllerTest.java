package com.kramekk1.medicalClinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
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
public class DoctorControllerTest {

    @MockitoBean
    private DoctorService doctorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getAll_doctorDtoListReturnedWithStatus200() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());
        DoctorDTO doctorDTO1 = new DoctorDTO(2L, "email1", "firstname1", "surname1", SpecializationType.CARDIO, "123456789", List.of());

        Page<DoctorDTO> page = new PageImpl<>(List.of(doctorDTO, doctorDTO1));

        when(doctorService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    public void getById_doctorDtoReturnedWithStatus200() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());

        when(doctorService.getById(any())).thenReturn(doctorDTO);
        mockMvc.perform(get("/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("firstname"))
                .andExpect(jsonPath("$.surname").value("surname"));
    }

    @Test
    public void create_doctorDtoStatus201Returned() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());
        CreateDoctorCommand command = new CreateDoctorCommand("email", "password", "firstname", "surname", SpecializationType.CARDIO, "123456789");

        when(doctorService.create(any())).thenReturn(doctorDTO);

        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email"));
    }

    @Test
    public void updateById_doctorDtoWithUpdatedFieldsReturned() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email1", "firstname1", "surname", SpecializationType.CARDIO, "123456789", List.of());
        UpdateDoctorCommand command = new UpdateDoctorCommand("email1", "password", "firstname1", "surname", SpecializationType.CARDIO, "123456789", new ArrayList<>());

        when(doctorService.update(any(), any())).thenReturn(doctorDTO);

        mockMvc.perform(put("/doctors/1")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email1"))
                .andExpect(jsonPath("$.firstname").value("firstname1"));
    }

    @Test
    public void deleteById_doctorDeleted() throws Exception {
        mockMvc.perform(delete("/doctors/1"))
                .andExpect(status().isNoContent());

        verify(doctorService).deleteById(1L);
    }

    @Test
    public void addInstitutionById_doctorDtoReturnedWithStatus200() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO(1L, "email", "firstname", "surname", SpecializationType.CARDIO, "123456789", List.of());
        AddInstitutionToDoctorCommand command = new AddInstitutionToDoctorCommand(1L);

        when(doctorService.addInstitutionToDoctorById(any(), any())).thenReturn(doctorDTO);

        mockMvc.perform(patch("/doctors/1")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email"));
    }
}

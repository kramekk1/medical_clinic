package com.kramekk1.medicalClinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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
public class PatientControllerTest {

    @MockitoBean
    private PatientService patientService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getAll_patientDtoListReturnedWithStatus200() throws Exception {
        PatientDTO patientDTO = new PatientDTO("email", "firstname", "lastname", "phoneNumber", LocalDate.now(), "fullname");
        PatientDTO patientDTO1 = new PatientDTO("email1", "firstname1", "lastname", "phoneNumber", LocalDate.now(), "fullname");

        when(patientService.getAll()).thenReturn(List.of(patientDTO, patientDTO1));

        mockMvc.perform(get("/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].email").value("email"))
                .andExpect(jsonPath("$[1].email").value("email1"));
    }

    @Test
    public void getPatientByEmail_patientDtoReturnedWithStatus200() throws Exception {
        PatientDTO patientDTO = new PatientDTO("email", "firstname", "lastname", "phoneNumber", LocalDate.now(), "fullname");

        when(patientService.getPatientByEmail(any())).thenReturn(patientDTO);

        mockMvc.perform(get("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email"));
    }

    @Test
    public void updatePatient_patientDtoReturnedWithStatus200() throws Exception {
        PatientDTO patientDTO = new PatientDTO("email", "firstname", "lastname", "phoneNumber", LocalDate.now(), "fullname");
        EditPatientCommand command = new EditPatientCommand("username", "password", "email", "idCardNo", "firstName", "lastName", "phoneNumber", LocalDate.now());

        when(patientService.editPatientByEmail(any(), any())).thenReturn(patientDTO);

        mockMvc.perform(put("/patients/email")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.firstName").value("firstname"));
    }

    @Test
    public void deletePatient_patientDeleted() throws Exception {
        mockMvc.perform(delete("/patients/email"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(patientService).deleteByEmail("email");
    }

    @Test
    public void editPasswordByEmail_passwordEdited() throws Exception {
        PatientPassword command = new PatientPassword("newPassword");

        mockMvc.perform(patch("/patients/email")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(patientService).editPasswordByEmail(any(), any());
    }

    @Test
    public void register_patientDtoReturnedWithStatus201() throws Exception {
        PatientDTO patientDTO = new PatientDTO("email", "firstname", "lastname", "phoneNumber", LocalDate.now(), "fullname");
        RegisterPatientCommand command = new RegisterPatientCommand("username", "password", "email", "idCardNo", "firstname", "lastname", "phoneNumber", LocalDate.now());

        when(patientService.register(any())).thenReturn(patientDTO);

        mockMvc.perform(post("/patients")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("email"));
    }
}

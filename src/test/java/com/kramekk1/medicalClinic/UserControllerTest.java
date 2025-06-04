package com.kramekk1.medicalClinic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(properties = "spring.liquibase.enabled=false")
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void add_userDtoReturnedWithStatus201() throws Exception {
        CreateUserCommand command = new CreateUserCommand("username", "password");
        UserDTO userDTO = new UserDTO("username");

        when(userService.add(any())).thenReturn(userDTO);

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    public void getAll_userDtoListReturnedWithStatus200() throws Exception {
        UserDTO userDTO = new UserDTO("username");
        UserDTO userDTO1 = new UserDTO("username1");

        when(userService.getAll()).thenReturn(List.of(userDTO, userDTO1));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(jsonPath("$[0].username").value("username"));
    }
}

package com.kramekk1.medicalClinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserRepository userRepository;
    UserMapper userMapper;
    UserService userService;

    @BeforeEach
    void setup() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.userMapper = Mappers.getMapper(UserMapper.class);
        this.userService = new UserService(userRepository, userMapper);
    }

    @Test
    void getAll_allExist_userDtoListReturned() {
        User user = new User(1L, "username", "password", new Patient());
        User user1 = new User(2L, "username1", "password1", new Patient());

        Pageable pageable = PageRequest.of(0,10);
        Page<User> page = new PageImpl<>(List.of(user, user1));

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<UserDTO> result = userService.getAll(pageable);

        Assertions.assertAll(
                () -> assertEquals("username", result.getContent().get(0).getUsername()),
                () -> assertEquals("username1", result.getContent().get(1).getUsername()),
                () -> assertEquals(2, result.getTotalElements())
        );
    }

    @Test
    void add_createCommandExist_userDtoReturned() {
        CreateUserCommand command = new CreateUserCommand("username", "password");
        User user = userMapper.toEntity(command);
        when(userRepository.save(any())).thenReturn(user);

        UserDTO result = userService.add(command);

        Assertions.assertAll(
                () -> assertEquals("username", result.getUsername())
        );
    }
}

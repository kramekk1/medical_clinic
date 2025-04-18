package com.kramekk1.medicalClinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserCommand command);

    UserDTO toDTO(User user);

    UserDTO toDTO(CreateUserCommand command);
}

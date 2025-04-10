package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
    public UserDTO add(CreateUserCommand command) {
        User userEntity = userMapper.toEntity(command);
        return userMapper.toDTO(userRepository.save(userEntity));
    }

}

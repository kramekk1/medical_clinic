package com.kramekk1.medicalClinic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    @Transactional
    public UserDTO add(CreateUserCommand command) {
        User userEntity = userMapper.toEntity(command);
        return userMapper.toDTO(userRepository.save(userEntity));
    }


}

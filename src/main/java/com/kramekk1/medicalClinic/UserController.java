package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public UserDTO add(@RequestBody CreateUserCommand command) {
        return userService.add(command);
    }

    @GetMapping
    public Page<UserDTO> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }

}

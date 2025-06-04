package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

}

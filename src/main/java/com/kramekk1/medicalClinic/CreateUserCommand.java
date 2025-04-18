package com.kramekk1.medicalClinic;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserCommand {
    private String username;
    private String password;
}

package com.kramekk1.medicalClinic;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ErrorMessage {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime occuredAt;
}

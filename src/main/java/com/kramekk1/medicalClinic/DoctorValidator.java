package com.kramekk1.medicalClinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DoctorValidator {

    public static void validateEmailDuplicate(String email, DoctorRepository doctorRepository) {
        if (doctorRepository.findByEmail(email).isPresent()) {
            throw new DoctorEmailDuplicateException("Doctor with this email already exist", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateNullField(CreateDoctorCommand command) {
        if (command.getEmail() == null || command.getPassword() == null || command.getFirstname() == null ||
                command.getSurname() == null || command.getSpecializationType() == null) {
            throw new DoctorNullFieldException("One or more data field is null", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateNullField(UpdateDoctorCommand command) {
        if (command.getEmail() == null || command.getPassword() == null || command.getFirstname() == null ||
                command.getSurname() == null || command.getSpecializationType() == null) {
            throw new DoctorNullFieldException("One or more data field is null", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.kramekk1.medicalClinic;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    public void update(Patient sourcePatient) {
        this.email = sourcePatient.getEmail();
        this.password = sourcePatient.getPassword();
        this.idCardNo = sourcePatient.getIdCardNo();
        this.firstName = sourcePatient.getFirstName();
        this.lastName = sourcePatient.getLastName();
        this.phoneNumber = sourcePatient.getPhoneNumber();
        this.birthday = sourcePatient.getBirthday();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}




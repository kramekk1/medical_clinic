package com.kramekk1.medicalClinic;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String firstname;
    private String surname;
    private SpecializationType specializationType;

    public void update(UpdateDoctorCommand command) {
        this.email = command.getEmail();
        this.password = command.getPassword();
        this.firstname = command.getFirstname();
        this.surname = command.getSurname();
        this.specializationType = command.getSpecializationType();
    }
}

package com.kramekk1.medicalClinic;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "doctor_institution",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id")
    )
    private List<Institution> institution;

    public void update(UpdateDoctorCommand command) {
        this.email = command.getEmail();
        this.password = command.getPassword();
        this.firstname = command.getFirstname();
        this.surname = command.getSurname();
        this.specializationType = command.getSpecializationType();
    }
}

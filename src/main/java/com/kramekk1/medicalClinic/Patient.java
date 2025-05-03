package com.kramekk1.medicalClinic;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Visit> visit;

    public void update(Patient sourcePatient) {
        this.email = sourcePatient.getEmail();
        this.user = sourcePatient.getUser();
        this.idCardNo = sourcePatient.getIdCardNo();
        this.firstName = sourcePatient.getFirstName();
        this.lastName = sourcePatient.getLastName();
        this.phoneNumber = sourcePatient.getPhoneNumber();
        this.birthday = sourcePatient.getBirthday();
    }

    public void updatePassword(String newPassword) {
        if (user != null) {
            user.setPassword(newPassword);
        }
    }

    public void register(RegisterPatientCommand command) {
        User user = new User();
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());

        Patient patient = new Patient();
        patient.setEmail(command.getEmail());
        patient.setIdCardNo(command.getIdCardNo());
        patient.setFirstName(command.getFirstName());
        patient.setLastName(command.getLastName());
        patient.setPhoneNumber(command.getPhoneNumber());
        patient.setBirthday(command.getBirthday());

        patient.setUser(user);
    }
}




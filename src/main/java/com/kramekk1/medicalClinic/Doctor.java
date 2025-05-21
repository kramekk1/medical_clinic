package com.kramekk1.medicalClinic;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
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

    @Enumerated(value = EnumType.STRING)
    private SpecializationType specializationType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "doctor_institution",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id")
    )
    private List<Institution> institutions = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Visit> visit;

    public void update(UpdateDoctorCommand command) {
        this.email = command.getEmail();
        this.password = command.getPassword();
        this.firstname = command.getFirstname();
        this.surname = command.getSurname();
        this.specializationType = command.getSpecializationType();
    }

    public void addInstitutionToDoctor(Institution institution) {
        this.institutions.add(institution);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor))
            return false;

        Doctor other = (Doctor) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

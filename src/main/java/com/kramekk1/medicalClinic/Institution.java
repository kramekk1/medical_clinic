package com.kramekk1.medicalClinic;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Institutions")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String city;
    private String address;
    private String postalCode;
    private String street;
    private String buildingNumber;

    @ManyToMany(mappedBy = "institutions")
    private List<Doctor> doctors = new ArrayList<>();

    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id;
    }


}

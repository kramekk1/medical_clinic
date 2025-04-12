package com.kramekk1.medicalClinic;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
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
    private String postalCode;
    private String street;
    private String buildingNumber;

    @ManyToMany(mappedBy = "institution")
    private List<Doctor> doctors = new ArrayList<>();
}

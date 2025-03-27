package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class InstitutionRepository {

    private final List<Institution> institutions = new ArrayList<>();

    public List<Institution> getAll() {
        return new ArrayList<>(institutions);
    }

    public Optional<Institution> findByName(String name) {
        return institutions.stream().filter(institution -> name.equals(institution.getName())).findFirst();
    }

    public void delete(String institutionName) {
        institutions.removeIf(institution -> institutionName.equals(institution.getName()));
    }

    public void add(Institution institution) {
        institutions.add(institution);
    }
}

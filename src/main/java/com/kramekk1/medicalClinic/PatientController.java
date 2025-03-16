package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    @GetMapping("/all")
    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }
    @GetMapping("/{email}")
    public Optional<Patient> getPatientByEmail(@PathVariable String email) {
        return patientRepository.findPatientByEmail(email);
    }

    @PostMapping("/add")
    public String addPatient(@RequestBody Patient patient) {
        patientRepository.addPatient(patient);
        return "Dodano pomyslnie";
    }
    @PutMapping("/{email}")
    public String updatePatient(@PathVariable String email, @RequestBody Patient newPatient) {
        patientService.editPatientByEmail(email, newPatient);
        return "Zaktualizowano pacjenta";
    }
    @DeleteMapping("/{email}")
    public String deletePatient(@PathVariable String email) {
        patientRepository.deletePatient(email);
        return "Usunieto pacjenta";
    }
}

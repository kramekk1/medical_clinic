package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getAll() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public DoctorDTO getById(@PathVariable Long id) {
        return doctorService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDTO create(@RequestBody CreateDoctorCommand doctor) {
        return doctorService.create(doctor);
    }

    @PutMapping("/{id}")
    public DoctorDTO updateById(@RequestBody UpdateDoctorCommand command, @PathVariable Long id) {
        return doctorService.update(command, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        doctorService.deleteById(id);
    }

    @PatchMapping("/{doctorId}")
    public DoctorDTO addInstitutionById(@PathVariable Long doctorId, @RequestBody AddInstitutionToDoctorCommand command) {
        return doctorService.addInstitutionToDoctorById(doctorId, command.getInstitutionId());
    }
}

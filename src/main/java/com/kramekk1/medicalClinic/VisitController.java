package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    public List<VisitDTO> getAll() {
        return visitService.getAll();
    }

    @GetMapping("/{patientId}")
    public List<VisitDTO> getByPatientId(@PathVariable Long patientId) {
        return visitService.getByPatientId(patientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitDTO create(@RequestBody CreateVisitCommand command) {
        return visitService.create(command);
    }

    @PatchMapping("/{visitId}")
    public VisitDTO assignPatient(@PathVariable Long visitId, @RequestBody AssignPatientCommand command) {
        return visitService.assignPatient(visitId, command.getPatientId());
    }

    @DeleteMapping("/{visitId}")
    public void deleteById(@PathVariable Long visitId) {
        visitService.deleteById(visitId);
    }
}

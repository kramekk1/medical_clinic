package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    public List<VisitDTO> getAll(Pageable pageable) {
        return visitService.getAll(pageable).getContent();
    }

    @GetMapping("/by-patient")
    public Page<VisitDTO> getByPatientId(@RequestParam Long patientId, Pageable pageable) {
        return visitService.getByPatientId(patientId, pageable);
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

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/by-doctor")
    public Page<VisitDTO> getByDoctorId(@RequestParam Long doctorId, Pageable pageable) {
        return visitService.getByDoctorId(doctorId, pageable);
    }

    @PatchMapping("/cancel/{visitId}")
    public VisitDTO cancel(@PathVariable Long visitId) {
        return visitService.cancelVisit(visitId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/refresh")
    public void refreshVisitsStatus() {
        visitService.refreshVisitsStatus();
    }
}

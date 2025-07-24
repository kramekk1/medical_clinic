package com.kramekk1.medicalClinic;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/available-visits")
@RequiredArgsConstructor
public class AvailableVisitController {

    private final VisitService visitService;


    @GetMapping("/by-doctor")
    public List<VisitDTO> getAvailableVisitsByDoctorId(@RequestParam Long doctorId) {
        return visitService.getByDoctorIdAndFreeStatus(doctorId);
    }

    @PostMapping("/spec-date")
    public List<VisitDTO> getAvailableVisitsBySpecializationAndDate(@RequestBody SpecializationAndDateFilterCommand filter) {
        return visitService.getAvailableVisitsBySpecializationAndDate(filter);
    }

    @PostMapping("/date")
    public List<VisitDTO> getAvailableVisitsByDate(@RequestBody DateFilterCommand filter) {
        return visitService.getAvailableVisitsByDate(filter);
    }
}

package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping
    public List<InstitutionDTO> getAll() {
        return institutionService.getAll();
    }

    @DeleteMapping("/{institutionName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String institutionName) {
        institutionService.delete(institutionName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstitutionDTO add(@RequestBody CreateInstitutionCommand institution) {
        return institutionService.add(institution);
    }
}

package com.kramekk1.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/institutions")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping
    public Page<InstitutionDTO> getAll(Pageable pageable) {
        return institutionService.getAll(pageable);
    }

    @DeleteMapping("/{institutionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long institutionId) {
        institutionService.deleteById(institutionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstitutionDTO create(@RequestBody CreateInstitutionCommand command) {
        return institutionService.create(command);
    }
}

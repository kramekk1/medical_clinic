package com.kramekk1.medicalClinic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Doctors", description = "Operations related to doctor")
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Get all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found doctors list",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DoctorDTO.class))
                    )})
    })
    @GetMapping
    public Page<DoctorDTO> getAll(Pageable pageable) {
        return doctorService.getAll(pageable);
    }

    @Operation(summary = "Get doctor by Id")
    @Parameter(description = "Doctor Id to find")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found doctor by Id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor with this Id not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public DoctorDTO getById(@PathVariable Long id) {
        return doctorService.getById(id);
    }

    @Operation(summary = "Create doctor")
    @RequestBody(description = "Creating Doctor body fields", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Doctor with this email already exist",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DoctorDTO create(@org.springframework.web.bind.annotation.RequestBody CreateDoctorCommand doctor) {
        return doctorService.create(doctor);
    }

    @Operation(summary = "Update doctor")
    @RequestBody(description = "Update Doctor body fields", required = true)
    @Parameter(description = "Doctor Id to find")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor with this Id not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public DoctorDTO updateById(@RequestBody UpdateDoctorCommand command, @PathVariable Long id) {
        return doctorService.update(command, id);
    }

    @Operation(summary = "Delete doctor")
    @Parameter(description = "Doctor Id to find")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor with this Id not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        doctorService.deleteById(id);
    }

    @Operation(summary = "Add institution to doctor")
    @Parameter(description = "Doctor Id to find")
    @RequestBody(description = "Institution Id which will be added", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Institution added to doctor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor or Institution with this Id not found",
                    content = @Content)
    })
    @PatchMapping("/{doctorId}")
    public DoctorDTO addInstitutionById(@PathVariable Long doctorId, @RequestBody AddInstitutionToDoctorCommand command) {
        return doctorService.addInstitutionToDoctorById(doctorId, command.getInstitutionId());
    }

    @GetMapping("/by-specialization")
    public List<DoctorDTO> getBySpecialization(@RequestParam SpecializationType specializationType) {
        return doctorService.getBySpecialization(specializationType);
    }
}

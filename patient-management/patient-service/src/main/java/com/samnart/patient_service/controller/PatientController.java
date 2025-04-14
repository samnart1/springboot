package com.samnart.patient_service.controller;

import java.util.List;
import java.util.UUID;

import org.hibernate.boot.model.internal.XMLContext.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samnart.patient_service.dto.PatientRequestDTO;
import com.samnart.patient_service.dto.PatientResponseDTO;
import com.samnart.patient_service.dto.validators.CreatePatientValidationGroup;
import com.samnart.patient_service.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patientDTO = patientService.getPatients();
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) { 
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{Id}")
    @Operation(summary = "Update an existing Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID Id, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO patientResponseDTO = patientService.updatePatient(Id, patientRequestDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{Id}")
    @Operation(summary = "Delete an existing Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID Id) {
        patientService.deletePatient(Id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}

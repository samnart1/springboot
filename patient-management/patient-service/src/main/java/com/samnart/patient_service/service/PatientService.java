package com.samnart.patient_service.service;

import java.util.List;
import java.util.UUID;

import com.samnart.patient_service.dto.PatientRequestDTO;
import com.samnart.patient_service.dto.PatientResponseDTO;

public interface PatientService {
    List<PatientResponseDTO> getPatients();
    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);
    PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO);
    void deletePatient(UUID id);
}

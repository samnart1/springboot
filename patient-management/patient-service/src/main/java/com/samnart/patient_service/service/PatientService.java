package com.samnart.patient_service.service;

import java.util.List;

import com.samnart.patient_service.dto.PatientResponseDTO;

public interface PatientService {
    List<PatientResponseDTO> getPatients();
}

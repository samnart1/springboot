package com.samnart.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.samnart.patient_service.Mapper.PatientMapper;
import com.samnart.patient_service.dto.PatientResponseDTO;
import com.samnart.patient_service.model.Patient;
import com.samnart.patient_service.repository.PatientRepository;

@Service
public class PatientServiceImp implements PatientService {
    private PatientRepository patientRepository;
    
    public PatientServiceImp(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTO = patients.stream().map(PatientMapper::toDTO).toList();

        return patientResponseDTO;
    }
}

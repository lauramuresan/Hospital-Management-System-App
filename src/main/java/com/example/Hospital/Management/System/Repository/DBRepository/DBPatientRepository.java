package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBPatientRepository extends JpaRepository<PatientEntity, Long> {
    boolean existsByPacientEmail(String pacientEmail);
}
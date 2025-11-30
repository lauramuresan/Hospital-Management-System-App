package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBPatientRepository extends JpaRepository<PatientEntity, Long> {

    // Metodă standard JpaRepository pentru a verifica existența după PacientEmail.
    boolean existsByPacientEmail(String pacientEmail);
}
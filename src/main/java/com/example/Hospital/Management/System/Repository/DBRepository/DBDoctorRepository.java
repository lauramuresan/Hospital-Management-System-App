package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.Model.Enums.MedicalSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBDoctorRepository extends JpaRepository<DoctorEntity, Long> {

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByStaffEmail(String staffEmail);
    boolean existsByStaffEmailAndMedicalSpeciality(String staffEmail, MedicalSpecialty medicalSpeciality);

}
package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffAppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBMedicalStaffAppointmentRepository extends JpaRepository<MedicalStaffAppointmentEntity, Long> {
}
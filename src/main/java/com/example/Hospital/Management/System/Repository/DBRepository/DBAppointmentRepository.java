package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
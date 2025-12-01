package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffAppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DBMedicalStaffAppointmentRepository extends JpaRepository<MedicalStaffAppointmentEntity, Long> {

    /**
     * Preia toate alocările, încărcând EAGER relațiile necesare
     * pentru a preveni LazyInitializationException în afara tranzacției.
     */
    @Query("SELECT m FROM MedicalStaffAppointmentEntity m " +
            "LEFT JOIN FETCH m.appointment " +
            "LEFT JOIN FETCH m.doctor " +
            "LEFT JOIN FETCH m.nurse")
    List<MedicalStaffAppointmentEntity> findAllWithStaffAndAppointment();
}
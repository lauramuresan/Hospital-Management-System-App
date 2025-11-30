package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DBAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    // Metodă pentru a găsi programările care se suprapun într-o anumită cameră, într-un interval de timp dat.
    @Query("SELECT a FROM AppointmentEntity a WHERE a.room.id = :roomId AND a.admissionDate BETWEEN :startWindow AND :endWindow")
    List<AppointmentEntity> findByRoomIdAndAppointmentDateTimeBetween(
            @Param("roomId") Long roomId,
            @Param("startWindow") LocalDateTime startWindow,
            @Param("endWindow") LocalDateTime endWindow);
}
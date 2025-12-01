package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBRoomRepository extends JpaRepository<RoomEntity, Long> {

    boolean existsByNumberAndHospitalId(String number, Long hospitalId);
}
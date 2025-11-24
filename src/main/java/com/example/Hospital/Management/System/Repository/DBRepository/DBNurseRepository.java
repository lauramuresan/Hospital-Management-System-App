package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBNurseRepository extends JpaRepository<NurseEntity, Long> {
}
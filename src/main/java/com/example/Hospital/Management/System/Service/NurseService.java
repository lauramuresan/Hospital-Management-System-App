package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Nurse;
import com.example.Hospital.Management.System.Repository.NurseRepository;

import java.util.List;

public class NurseService {
    private final NurseRepository nurseRepository;
    public NurseService(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }
    public void addNurse(Nurse nurse) {
        nurseRepository.save(nurse);
    }
    public void removeNurse(String nurseId) {
        Nurse nurse = nurseRepository.findById(nurseId);
        if (nurse != null) nurseRepository.delete(nurse);
    }
    public Nurse getNurseById(String id) {
        return nurseRepository.findById(id);
    }
    public List<Nurse> getAllnurses() {
        return nurseRepository.findAll();
    }

}

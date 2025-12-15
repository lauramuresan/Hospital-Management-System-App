package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder;
import com.example.Hospital.Management.System.Repository.RepositoryMode;
import com.example.Hospital.Management.System.Utils.ReflectionSorter;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HospitalService extends BaseService<Hospital>{

    private final AbstractRepository<Hospital> hospitalRepository;
    private final RepositoryModeHolder modeHolder;

    public HospitalService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
        this.hospitalRepository = factory.createRepository(Hospital.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Hospital entity){
        hospitalRepository.save(entity);
    }

    @Override
    protected void delete(Hospital entity){
        hospitalRepository.delete(entity);
    }

    @Override
    public Hospital findById(String id){
        return hospitalRepository.findById(id);
    }

    @Override
    protected List<Hospital> findAll(){
        return hospitalRepository.findAll();
    }

    @Override
    protected List<Hospital> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return hospitalRepository.findAll(sort);
        } else {
            List<Hospital> hospitals = hospitalRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(hospitals, Hospital.class, sort);
            }
            return hospitals;
        }
    }
}
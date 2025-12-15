package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryMode; // IMPORT NOU
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder; // IMPORT NOU
import com.example.Hospital.Management.System.Utils.ReflectionSorter; // IMPORT NOU

import org.springframework.data.domain.Sort; // IMPORT NOU
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class NurseService extends BaseService<Nurse> {
    private final AbstractRepository<Nurse> nurseRepository;
    private final RepositoryModeHolder modeHolder; // INJECTIE NOUA

    public NurseService(RepositoryFactory factory, RepositoryModeHolder modeHolder) { // MODIFICAT CONSTRUCTOR
        this.nurseRepository = factory.createRepository(Nurse.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Nurse entity){
        nurseRepository.save(entity);
    }
    @Override
    protected void delete(Nurse entity){
        nurseRepository.delete(entity);
    }
    @Override
    public Nurse findById(String id){
        return nurseRepository.findById(id);
    }
    @Override
    public List<Nurse> findAll(){
        return nurseRepository.findAll();
    }


    @Override
    protected List<Nurse> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return nurseRepository.findAll(sort);
        } else {
            List<Nurse> nurses = nurseRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(nurses, Nurse.class, sort);
            }
            return nurses;
        }
    }
}
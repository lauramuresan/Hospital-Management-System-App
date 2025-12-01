package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DepartmentMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity; // Asigurați-vă că este importat
import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDepartmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBHospitalRepository;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityNotFoundException; // Adăugat pentru excepții clare

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DepartmentAdaptor implements AbstractRepository<Department> {

    private final DBDepartmentRepository jpaRepository;
    private final DBHospitalRepository hospitalJpaRepository;

    public DepartmentAdaptor(DBDepartmentRepository jpaRepository, DBHospitalRepository hospitalJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.hospitalJpaRepository = hospitalJpaRepository;
    }

    @Override
    public void save(Department domain) {
        Long hospitalId = MapperUtils.parseLong(domain.getHospitalID());

        // 1. Validare existență Spital părinte (FK)
        if (hospitalId == null || !hospitalJpaRepository.existsById(hospitalId)) {
            // Aruncăm o excepție clară, care va fi prinsă de stratul superior
            throw new EntityNotFoundException("Spitalul specificat cu ID-ul " + domain.getHospitalID() + " nu există.");
        }

        // 2. Validare Unicitate Nume Departamnt pe Spital (Business Validation)
        Optional<DepartmentEntity> existingDepartment = jpaRepository.findByDepartmentNameAndHospitalId(domain.getDepartmentName(), hospitalId);

        if (existingDepartment.isPresent()) {
            // Verificăm dacă nu e vorba de UPDATE-ul aceleiași entități
            if (domain.getDepartmentID() == null || !existingDepartment.get().getId().equals(MapperUtils.parseLong(domain.getDepartmentID()))) {
                throw new RuntimeException("Există deja un departament cu numele " + domain.getDepartmentName() + " în acest spital.");
            }
        }

        // 3. Crearea entității și ATAȘAREA REFERINȚEI SPITALULUI
        DepartmentEntity entity = DepartmentMapper.toEntity(domain);

        // Obținem o REFERINȚĂ (proxy) la HospitalEntity validă, fără a o încărca din baza de date
        // Aceasta este esențial pentru a seta corect Foreign Key.
        HospitalEntity hospitalReference = hospitalJpaRepository.getReferenceById(hospitalId);
        entity.setHospital(hospitalReference);

        // 4. Salvarea entității (INSERT)
        jpaRepository.save(entity);
    }

    @Override
    public void delete(Department domain) {
        if (domain.getDepartmentID() != null) {
            jpaRepository.deleteById(MapperUtils.parseLong(domain.getDepartmentID()));
        }
    }

    @Override
    public Department findById(String id) {
        try {
            return jpaRepository.findById(MapperUtils.parseLong(id))
                    .map(DepartmentMapper::toDomain)
                    .orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Department> findAll() {
        return jpaRepository.findAll().stream()
                .map(DepartmentMapper::toDomain)
                .collect(Collectors.toList());
    }
}
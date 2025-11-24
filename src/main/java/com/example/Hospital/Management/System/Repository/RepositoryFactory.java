package com.example.Hospital.Management.System.Repository;

import com.example.Hospital.Management.System.Model.GeneralModel.*;
import com.example.Hospital.Management.System.Repository.DBRepository.*;
import com.example.Hospital.Management.System.Repository.InFile.*;
import com.example.Hospital.Management.System.Repository.InMemory.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RepositoryFactory {

    private final RepositoryModeHolder modeHolder;

    private final Map<String, RepoSet<?>> repoMap = new HashMap<>();

    public RepositoryFactory(
            RepositoryModeHolder modeHolder,
            AppointmentInMemoryRepository appointmentInMemory, AppointmentInFileRepository appointmentInFile,
            DepartmentInMemoryRepository departmentInMemory, DepartmentInFileRepository departmentInFile,
            DoctorInMemoryRepository doctorInMemory, DoctorInFileRepository doctorInFile,
            HospitalInMemoryRepository hospitalInMemory, HospitalInFileRepository hospitalInFile,
            MedicalStaffAppointmentInMemoryRepository msaInMemory, MedicalStaffAppointmentInFileRepository msaInFile,
            NurseInMemoryRepository nurseInMemory, NurseInFileRepository nurseInFile,
            PatientInMemoryRepository patientInMemory, PatientInFileRepository patientInFile,
            RoomInMemoryRepository roomInMemory, RoomInFileRepository roomInFile,

            AppointmentJpaAdapterRepository appointmentJpaAdapter,
            DepartmentJpaAdapterRepository departmentJpaAdapter,
            DoctorJpaAdapterRepository doctorJpaAdapter,
            HospitalJpaAdapterRepository hospitalJpaAdapter,
            MedicalStaffAppointmentJpaAdapterRepository msaJpaAdapter,
            NurseJpaAdapterRepository nurseJpaAdapter,
            PatientJpaAdapterRepository patientJpaAdapter,
            RoomJpaAdapterRepository roomJpaAdapter
    ) {
        this.modeHolder = modeHolder;

        repoMap.put(Appointment.class.getSimpleName(), new RepoSet<>(appointmentInMemory, appointmentInFile, appointmentJpaAdapter));
        repoMap.put(Department.class.getSimpleName(), new RepoSet<>(departmentInMemory, departmentInFile, departmentJpaAdapter));
        repoMap.put(Doctor.class.getSimpleName(), new RepoSet<>(doctorInMemory, doctorInFile, doctorJpaAdapter));
        repoMap.put(Hospital.class.getSimpleName(), new RepoSet<>(hospitalInMemory, hospitalInFile, hospitalJpaAdapter));
        repoMap.put(MedicalStaffAppointment.class.getSimpleName(), new RepoSet<>(msaInMemory, msaInFile, msaJpaAdapter));
        repoMap.put(Nurse.class.getSimpleName(), new RepoSet<>(nurseInMemory, nurseInFile, nurseJpaAdapter));
        repoMap.put(Patient.class.getSimpleName(), new RepoSet<>(patientInMemory, patientInFile, patientJpaAdapter));
        repoMap.put(Room.class.getSimpleName(), new RepoSet<>(roomInMemory, roomInFile, roomJpaAdapter));
    }

    public <T> AbstractRepository<T> createRepository(Class<T> entityType) {
        RepoSet<T> set = (RepoSet<T>) repoMap.get(entityType.getSimpleName());

        if (set == null) {
            throw new IllegalArgumentException("No repository registered for type: " + entityType.getSimpleName());
        }

        return new AbstractRepository<>() {

            private AbstractRepository<T> activeRepo() {
                RepositoryMode mode = modeHolder.getMode();

                if (mode == RepositoryMode.INFILE) {
                    return set.inFileRepo();
                } else if (mode == RepositoryMode.MYSQL) {
                    return set.jpaAdapterRepo(); // Utilizează adaptorul JPA
                } else {
                    return set.inMemoryRepo();
                }
            }

            @Override public void save(T entity) { activeRepo().save(entity); }
            @Override public void delete(T entity) { activeRepo().delete(entity); }
            @Override public T findById(String id) { return activeRepo().findById(id); }
            @Override public java.util.List<T> findAll() { return activeRepo().findAll(); }
        };
    }

    private static final class RepoSet<T> {
        private final AbstractRepository<T> inMemoryRepo;
        private final AbstractRepository<T> inFileRepo;
        private final AbstractRepository<T> jpaAdapterRepo;

        public RepoSet(AbstractRepository<T> inMemoryRepo, AbstractRepository<T> inFileRepo, AbstractRepository<T> jpaAdapterRepo) {
            this.inMemoryRepo = inMemoryRepo;
            this.inFileRepo = inFileRepo;
            this.jpaAdapterRepo = jpaAdapterRepo;
        }

        public AbstractRepository<T> inMemoryRepo() { return inMemoryRepo; }
        public AbstractRepository<T> inFileRepo() { return inFileRepo; }
        public AbstractRepository<T> jpaAdapterRepo() { return jpaAdapterRepo; }
    }
}
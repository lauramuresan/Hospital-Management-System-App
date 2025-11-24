package com.example.Hospital.Management.System.Repository;

import com.example.Hospital.Management.System.Model.GeneralModel.*;
import com.example.Hospital.Management.System.Repository.InFile.*;
import com.example.Hospital.Management.System.Repository.InMemory.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RepositoryFactory {

    private final RepositoryModeHolder modeHolder;


    private final Map<Class<?>, RepoPair<?>> repoMap = new HashMap<>();

    public RepositoryFactory(
            RepositoryModeHolder modeHolder,
            AppointmentInMemoryRepository appointmentInMemory,
            AppointmentInFileRepository appointmentInFile,
            DepartmentInMemoryRepository departmentInMemory,
            DepartmentInFileRepository departmentInFile,
            DoctorInMemoryRepository doctorInMemory,
            DoctorInFileRepository doctorInFile,
            HospitalInMemoryRepository hospitalInMemory,
            HospitalInFileRepository hospitalInFile,
            MedicalStaffAppointmentInMemoryRepository msaInMemory,
            MedicalStaffAppointmentInFileRepository msaInFile,
            NurseInMemoryRepository nurseInMemory,
            NurseInFileRepository nurseInFile,
            PatientInMemoryRepository patientInMemory,
            PatientInFileRepository patientInFile,
            RoomInMemoryRepository roomInMemory,
            RoomInFileRepository roomInFile
    ) {
        this.modeHolder = modeHolder;

        repoMap.put(Appointment.class, new RepoPair<>(appointmentInMemory, appointmentInFile));
        repoMap.put(Department.class, new RepoPair<>(departmentInMemory, departmentInFile));
        repoMap.put(Doctor.class, new RepoPair<>(doctorInMemory, doctorInFile));
        repoMap.put(Hospital.class, new RepoPair<>(hospitalInMemory, hospitalInFile));
        repoMap.put(MedicalStaffAppointment.class, new RepoPair<>(msaInMemory, msaInFile));
        repoMap.put(Nurse.class, new RepoPair<>(nurseInMemory, nurseInFile));
        repoMap.put(Patient.class, new RepoPair<>(patientInMemory, patientInFile));
        repoMap.put(Room.class, new RepoPair<>(roomInMemory, roomInFile));
    }

    public <T> AbstractRepository<T> createRepository(Class<T> entityType) {
        RepoPair<T> pair = (RepoPair<T>) repoMap.get(entityType);
        if (pair == null) {
            throw new IllegalArgumentException("No repository registered for type: " + entityType.getSimpleName());
        }

        return new AbstractRepository<>() {
            @Override
            public void save(T entity) {
                activeRepo().save(entity);
            }

            @Override
            public void delete(T entity) {
                activeRepo().delete(entity);
            }

            @Override
            public T findById(String id) {
                return activeRepo().findById(id);
            }

            @Override
            public java.util.List<T> findAll() {
                return activeRepo().findAll();
            }

            private AbstractRepository<T> activeRepo() {
                return modeHolder.getMode() == RepositoryMode.INFILE
                        ? pair.inFileRepo()
                        : pair.inMemoryRepo();
            }
        };
    }

    private static final class RepoPair<T> {
        private final AbstractRepository<T> inMemoryRepo;
        private final AbstractRepository<T> inFileRepo;

        public RepoPair(AbstractRepository<T> inMemoryRepo, AbstractRepository<T> inFileRepo) {
            this.inMemoryRepo = inMemoryRepo;
            this.inFileRepo = inFileRepo;
        }

        public AbstractRepository<T> inMemoryRepo() { return inMemoryRepo; }
        public AbstractRepository<T> inFileRepo() { return inFileRepo; }


    }
}
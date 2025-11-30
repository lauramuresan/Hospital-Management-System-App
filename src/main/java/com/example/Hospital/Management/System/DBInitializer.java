package com.example.Hospital.Management.System;

import com.example.Hospital.Management.System.Model.DBModel.*;
import com.example.Hospital.Management.System.Model.Enums.AppointmentStatus;
import com.example.Hospital.Management.System.Model.Enums.MedicalSpecialty;
import com.example.Hospital.Management.System.Model.Enums.RoomAvailability;
import com.example.Hospital.Management.System.Model.Enums.NurseLevelQualification; // Importat
import com.example.Hospital.Management.System.Repository.DBRepository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBInitializer implements CommandLineRunner {

    private final DBHospitalRepository hospitalRepository;
    private final DBDepartmentRepository departmentRepository;
    private final DBRoomRepository roomRepository;
    private final DBPatientRepository patientRepository;
    private final DBDoctorRepository doctorRepository;
    private final DBNurseRepository nurseRepository;
    private final DBAppointmentRepository appointmentRepository;
    private final DBMedicalStaffAppointmentRepository staffAppointmentRepository;

    public DBInitializer(DBHospitalRepository hospitalRepository, DBDepartmentRepository departmentRepository,
                               DBRoomRepository roomRepository, DBPatientRepository patientRepository,
                               DBDoctorRepository doctorRepository, DBNurseRepository nurseRepository,
                               DBAppointmentRepository appointmentRepository, DBMedicalStaffAppointmentRepository staffAppointmentRepository) {
        this.hospitalRepository = hospitalRepository;
        this.departmentRepository = departmentRepository;
        this.roomRepository = roomRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.appointmentRepository = appointmentRepository;
        this.staffAppointmentRepository = staffAppointmentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (hospitalRepository.count() > 0) {
            System.out.println("--- Baza de date conține deja date. Sărit peste Initializare. ---");
            return;
        }



        HospitalEntity h1 = new HospitalEntity("Spitalul Terapie Intensiva", "Cluj-Napoca", new ArrayList<>(), new ArrayList<>());
        HospitalEntity h2 = new HospitalEntity("Clinica Pediatrica", "Bucuresti", new ArrayList<>(), new ArrayList<>());
        HospitalEntity h3 = new HospitalEntity("Maternitatea", "Cluj-Napoca", new ArrayList<>(), new ArrayList<>());

        List<HospitalEntity> hospitals = hospitalRepository.saveAll(List.of(h1, h2, h3));

        DepartmentEntity dep1 = new DepartmentEntity("Cardiologie", h1);
        DepartmentEntity dep2 = new DepartmentEntity("Neurologie", h1);
        DepartmentEntity dep3 = new DepartmentEntity("Pediatrie", h2);
        DepartmentEntity dep4 = new DepartmentEntity("Oncologie", h1);
        DepartmentEntity dep5 = new DepartmentEntity("Dermatologie", h3);
        DepartmentEntity dep6 = new DepartmentEntity("Urologie", h1);
        DepartmentEntity dep7 = new DepartmentEntity("Gastroenterologie", h2);
        DepartmentEntity dep8 = new DepartmentEntity("Oftalmologie", h3);
        DepartmentEntity dep9 = new DepartmentEntity("Ortopedie", h1);
        DepartmentEntity dep10 = new DepartmentEntity("Chirurgie Generala", h2);
        DepartmentEntity dep11 = new DepartmentEntity("ORL", h3);
        DepartmentEntity dep12 = new DepartmentEntity("Endocrinologie", h1);

        List<DepartmentEntity> departments = List.of(dep1, dep2, dep3, dep4, dep5, dep6, dep7, dep8, dep9, dep10, dep11, dep12);
        List<DepartmentEntity> savedDepartments = departmentRepository.saveAll(departments);

        h1.getDepartments().addAll(List.of(dep1, dep2, dep4, dep6, dep9, dep12));
        h2.getDepartments().addAll(List.of(dep3, dep7, dep10));
        h3.getDepartments().addAll(List.of(dep5, dep8, dep11));

        hospitalRepository.saveAll(hospitals);

        RoomEntity r1 = new RoomEntity("A101", 1, h1, RoomAvailability.AVAILABLE);
        RoomEntity r2 = new RoomEntity("A102", 2, h1, RoomAvailability.AVAILABLE);
        RoomEntity r3 = new RoomEntity("B201", 3, h2, RoomAvailability.OCCUPIED);
        RoomEntity r4 = new RoomEntity("B202", 2, h2, RoomAvailability.AVAILABLE);
        RoomEntity r5 = new RoomEntity("C301", 1, h3, RoomAvailability.AVAILABLE);
        RoomEntity r6 = new RoomEntity("C302", 2, h3, RoomAvailability.AVAILABLE);
        RoomEntity r7 = new RoomEntity("D401", 4, h1, RoomAvailability.AVAILABLE);
        RoomEntity r8 = new RoomEntity("D402", 2, h1, RoomAvailability.AVAILABLE);
        RoomEntity r9 = new RoomEntity("E501", 1, h2, RoomAvailability.AVAILABLE);
        RoomEntity r10 = new RoomEntity("E502", 3, h3, RoomAvailability.AVAILABLE);
        RoomEntity r11 = new RoomEntity("F601", 2, h1, RoomAvailability.AVAILABLE);

        List<RoomEntity> rooms = List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11);
        List<RoomEntity> savedRooms = roomRepository.saveAll(rooms);


        PatientEntity p1 = new PatientEntity("Popescu Ioan", "ioan.pop@mail.com", LocalDate.of(1975, 10, 10));
        PatientEntity p2 = new PatientEntity("Vasilescu Ana", "ana.vas@mail.com", LocalDate.of(1990, 5, 20));
        PatientEntity p3 = new PatientEntity("Ionescu Maria", "maria.i@mail.com", LocalDate.of(1960, 1, 15));
        PatientEntity p4 = new PatientEntity("Gheorghe Tudor", "tudor.g@mail.com", LocalDate.of(2005, 3, 25));
        PatientEntity p5 = new PatientEntity("Mihai Vasile", "vasi.m@mail.com", LocalDate.of(1988, 11, 8));
        PatientEntity p6 = new PatientEntity("Lupu Elena", "elena.lupu@mail.com", LocalDate.of(1995, 7, 12));
        PatientEntity p7 = new PatientEntity("Stancu Adrian", "adrian.s@mail.com", LocalDate.of(1982, 4, 30));
        PatientEntity p8 = new PatientEntity("Dinu Simona", "simona.d@mail.com", LocalDate.of(1979, 9, 1));
        PatientEntity p9 = new PatientEntity("Neagu Cosmin", "cosmin.n@mail.com", LocalDate.of(2010, 6, 18));
        PatientEntity p10 = new PatientEntity("Radu Andreea", "andreea.r@mail.com", LocalDate.of(1993, 2, 7));
        PatientEntity p11 = new PatientEntity("Petrescu Calin", "calin.p@mail.com", LocalDate.of(1968, 12, 5));

        List<PatientEntity> patients = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
        List<PatientEntity> savedPatients = patientRepository.saveAll(patients);

        DoctorEntity d1 = new DoctorEntity("Dr. Popescu", "dr.pop@mail.com", dep1, "D1001", MedicalSpecialty.CARDIOLOGY);
        DoctorEntity d2 = new DoctorEntity("Dr. Vasilescu", "dr.vas@mail.com", dep2, "D1002", MedicalSpecialty.NEUROLOGY);
        DoctorEntity d3 = new DoctorEntity("Dr. Ionescu", "dr.io@mail.com", dep3, "D1003", MedicalSpecialty.PEDRIATICS);
        DoctorEntity d4 = new DoctorEntity("Dr. Gheorghe", "dr.gh@mail.com", dep4, "D1004", MedicalSpecialty.ONCOLOGY);
        DoctorEntity d5 = new DoctorEntity("Dr. Mihai", "dr.mih@mail.com", dep5, "D1005", MedicalSpecialty.DERMATOLOGY);
        DoctorEntity d6 = new DoctorEntity("Dr. Lupu", "dr.lu@mail.com", dep6, "D1006", MedicalSpecialty.UROLOGY);
        DoctorEntity d7 = new DoctorEntity("Dr. Stancu", "dr.st@mail.com", dep7, "D1007", MedicalSpecialty.GASTROENTEROLOGY);
        DoctorEntity d8 = new DoctorEntity("Dr. Dinu", "dr.di@mail.com", dep8, "D1008", MedicalSpecialty.OPHTHALMOLOGY);
        DoctorEntity d9 = new DoctorEntity("Dr. Neagu", "dr.ne@mail.com", dep9, "D1009", MedicalSpecialty.ORTHOPEDICS);
        DoctorEntity d10 = new DoctorEntity("Dr. Radu", "dr.ra@mail.com", dep10, "D1010", MedicalSpecialty.GENERAL_SURGERY);
        DoctorEntity d11 = new DoctorEntity("Dr. Petrescu", "dr.pet@mail.com", dep11, "D1011", MedicalSpecialty.OTORHINOLARYNGOLOGY);

        List<DoctorEntity> doctors = List.of(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11);
        List<DoctorEntity> savedDoctors = doctorRepository.saveAll(doctors);

        NurseEntity n1 = new NurseEntity("As. Andreea", "as.andreea@mail.com", dep1, NurseLevelQualification.REGISTERED);
        NurseEntity n2 = new NurseEntity("As. Cosmin", "as.cosmin@mail.com", dep2, NurseLevelQualification.PRACTICAL);
        NurseEntity n3 = new NurseEntity("As. Elena", "as.elena@mail.com", dep3, NurseLevelQualification.REGISTERED);
        NurseEntity n4 = new NurseEntity("As. Vlad", "as.vlad@mail.com", dep4, NurseLevelQualification.PRACTICAL);
        NurseEntity n5 = new NurseEntity("As. Simona", "as.simona@mail.com", dep5, NurseLevelQualification.REGISTERED);
        NurseEntity n6 = new NurseEntity("As. Tudor", "as.tudor@mail.com", dep6, NurseLevelQualification.PRACTICAL);
        NurseEntity n7 = new NurseEntity("As. Maria", "as.maria@mail.com", dep7, NurseLevelQualification.REGISTERED);
        NurseEntity n8 = new NurseEntity("As. Ioana", "as.ioana@mail.com", dep8, NurseLevelQualification.PRACTICAL);
        NurseEntity n9 = new NurseEntity("As. Adrian", "as.adrian@mail.com", dep9, NurseLevelQualification.REGISTERED);
        NurseEntity n10 = new NurseEntity("As. Ana", "as.ana@mail.com", dep10, NurseLevelQualification.PRACTICAL);
        NurseEntity n11 = new NurseEntity("As. Vasile", "as.vasile@mail.com", dep11, NurseLevelQualification.REGISTERED);
        NurseEntity n12 = new NurseEntity("As. Dan", "as.dan@mail.com", dep12, NurseLevelQualification.PRACTICAL);

        List<NurseEntity> nurses = List.of(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);
        List<NurseEntity> savedNurses = nurseRepository.saveAll(nurses);

        AppointmentEntity a1 = new AppointmentEntity(LocalDateTime.now().plusDays(1).plusHours(10), AppointmentStatus.ACTIVE, p1, r1);
        AppointmentEntity a2 = new AppointmentEntity(LocalDateTime.now().plusDays(2).plusHours(11), AppointmentStatus.ACTIVE, p2, r2);
        AppointmentEntity a3 = new AppointmentEntity(LocalDateTime.now().minusDays(3).plusHours(9), AppointmentStatus.COMPLETED, p3, r3);
        AppointmentEntity a4 = new AppointmentEntity(LocalDateTime.now().plusDays(5).plusHours(14), AppointmentStatus.ACTIVE, p4, r4);
        AppointmentEntity a5 = new AppointmentEntity(LocalDateTime.now().plusDays(6).plusHours(15), AppointmentStatus.ACTIVE, p5, r5);
        AppointmentEntity a6 = new AppointmentEntity(LocalDateTime.now().plusDays(7).plusHours(8), AppointmentStatus.ACTIVE, p6, r6);
        AppointmentEntity a7 = new AppointmentEntity(LocalDateTime.now().minusDays(1).plusHours(16), AppointmentStatus.COMPLETED, p7, r7);
        AppointmentEntity a8 = new AppointmentEntity(LocalDateTime.now().plusDays(9).plusHours(12), AppointmentStatus.ACTIVE, p8, r8);
        AppointmentEntity a9 = new AppointmentEntity(LocalDateTime.now().plusDays(10).plusHours(13), AppointmentStatus.ACTIVE, p9, r9);
        AppointmentEntity a10 = new AppointmentEntity(LocalDateTime.now().plusDays(11).plusHours(10), AppointmentStatus.ACTIVE, p10, r10);
        AppointmentEntity a11 = new AppointmentEntity(LocalDateTime.now().plusDays(12).plusHours(11), AppointmentStatus.ACTIVE, p11, r11);
        AppointmentEntity a12 = new AppointmentEntity(LocalDateTime.now().plusDays(13).plusHours(14), AppointmentStatus.ACTIVE, p1, r1);

        List<AppointmentEntity> appointments = List.of(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
        List<AppointmentEntity> savedAppointments = appointmentRepository.saveAll(appointments);

        MedicalStaffAppointmentEntity msa1 = new MedicalStaffAppointmentEntity(a1, d1, n1);
        MedicalStaffAppointmentEntity msa2 = new MedicalStaffAppointmentEntity(a2, d2, n2);
        MedicalStaffAppointmentEntity msa3 = new MedicalStaffAppointmentEntity(a3, d3, n3);
        MedicalStaffAppointmentEntity msa4 = new MedicalStaffAppointmentEntity(a4, d4, n4);
        MedicalStaffAppointmentEntity msa5 = new MedicalStaffAppointmentEntity(a5, d5, n5);
        MedicalStaffAppointmentEntity msa6 = new MedicalStaffAppointmentEntity(a6, d6, n6);
        MedicalStaffAppointmentEntity msa7 = new MedicalStaffAppointmentEntity(a7, d7, n7);
        MedicalStaffAppointmentEntity msa8 = new MedicalStaffAppointmentEntity(a8, d8, n8);
        MedicalStaffAppointmentEntity msa9 = new MedicalStaffAppointmentEntity(a9, d9, n9);
        MedicalStaffAppointmentEntity msa10 = new MedicalStaffAppointmentEntity(a10, d10, n10);

        MedicalStaffAppointmentEntity msa11 = new MedicalStaffAppointmentEntity(a11, d11, null);
        MedicalStaffAppointmentEntity msa12 = new MedicalStaffAppointmentEntity(a12, d1, n2);

        List<MedicalStaffAppointmentEntity> staffAppointments = List.of(msa1, msa2, msa3, msa4, msa5, msa6, msa7, msa8, msa9, msa10, msa11, msa12);
        staffAppointmentRepository.saveAll(staffAppointments);

        System.out.println("--- SUCCES: Baza de date inițializată cu date explicite. ---");
    }
}
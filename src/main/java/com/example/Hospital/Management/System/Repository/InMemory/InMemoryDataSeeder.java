package com.example.Hospital.Management.System.Repository.InMemory;


import com.example.Hospital.Management.System.Model.*;
import com.example.Hospital.Management.System.Model.Enums.*;
import com.example.Hospital.Management.System.Repository.InMemory.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryDataSeeder {

    private final AppointmentInMemoryRepository appointmentRepo;
    private final DepartmentInMemoryRepository departmentRepo;
    private final DoctorInMemoryRepository doctorRepo;
    private final NurseInMemoryRepository nurseRepo;
    private final PatientInMemoryRepository patientRepo;
    private final HospitalInMemoryRepository hospitalRepo;
    private final RoomInMemoryRepository roomRepo;
    private final MedicalStaffAppointmentInMemoryRepository msaRepo;

    public InMemoryDataSeeder(
            AppointmentInMemoryRepository appointmentRepo,
            DepartmentInMemoryRepository departmentRepo,
            DoctorInMemoryRepository doctorRepo,
            NurseInMemoryRepository nurseRepo,
            PatientInMemoryRepository patientRepo,
            HospitalInMemoryRepository hospitalRepo,
            RoomInMemoryRepository roomRepo,
            MedicalStaffAppointmentInMemoryRepository msaRepo
    ) {
        this.appointmentRepo = appointmentRepo;
        this.departmentRepo = departmentRepo;
        this.doctorRepo = doctorRepo;
        this.nurseRepo = nurseRepo;
        this.patientRepo = patientRepo;
        this.hospitalRepo = hospitalRepo;
        this.roomRepo = roomRepo;
        this.msaRepo = msaRepo;
    }

    @PostConstruct
    public void seed() {

        seedHospitals();
        seedDepartments();
        seedPatients();
        seedDoctors();
        seedNurses();
        seedRooms();
        seedAppointments();
        seedMedicalStaffAppointments();

    }

    private void seedHospitals() {
        for (int i = 1; i <= 10; i++) {
            hospitalRepo.save(new Hospital(
                    "H" + i,
                    "Hospital " + i,
                    "City " + i
            ));
        }
    }

    private void seedDepartments() {
        for (int i = 1; i <= 10; i++) {
            departmentRepo.save(new Department(
                    "D" + i,
                    "Department " + i,
                    "H" + ((i % 10) + 1)
            ));
        }
    }

    private void seedPatients() {
        for (int i = 1; i <= 10; i++) {
            patientRepo.save(new Patient(
                    "P" + i,
                    "Patient " + i,
                    new ArrayList<>(),
                    "patient" + i + "@mail.com",
                    "1990-01-" + ((i % 28) + 1)
            ));
        }
    }

    private void seedDoctors() {
        MedicalSpecialty[] specialties = MedicalSpecialty.values();

        for (int i = 1; i <= 10; i++) {
            doctorRepo.save(new Doctor(
                    "DOC" + i,
                    "D" + ((i % 10) + 1),
                    "Doctor " + i,
                    new ArrayList<>(),
                    "doctor" + i + "@mail.com",
                    "LIC-" + i,
                    specialties[i % specialties.length]
            ));
        }
    }

    private void seedNurses() {
        NurseLevelQualification[] levels = NurseLevelQualification.values();

        for (int i = 1; i <= 10; i++) {
            nurseRepo.save(new Nurse(
                    "NUR" + i,
                    "D" + ((i % 10) + 1),
                    "Nurse " + i,
                    new ArrayList<>(),
                    "nurse" + i + "@mail.com",
                    levels[i % levels.length]
            ));
        }
    }

    private void seedRooms() {
        RoomAvailability[] statuses = RoomAvailability.values();

        for (int i = 1; i <= 10; i++) {
            roomRepo.save(new Room(
                    "R" + i,
                    "H" + ((i % 10) + 1),
                    3,
                    "Room-" + i,
                    statuses[i % statuses.length],
                    new ArrayList<>()
            ));
        }
    }

    private void seedAppointments() {
        AppointmentStatus[] statuses = AppointmentStatus.values();

        for (int i = 1; i <= 10; i++) {
            appointmentRepo.save(new Appointment(
                    "A" + i,
                    "P" + ((i % 10) + 1),
                    "D" + ((i % 10) + 1),
                    LocalDateTime.now().plusDays(i),
                    statuses[i % statuses.length],
                    new ArrayList<>()
            ));
        }
    }

    private void seedMedicalStaffAppointments() {
        for (int i = 1; i <= 10; i++) {
            msaRepo.save(new MedicalStaffAppointment(
                    "MSA" + i,
                    "A" + ((i % 10) + 1),

                    // alternează doctori și asistente
                    (i % 2 == 0)
                            ? "DOC" + ((i % 10) + 1)
                            : "NUR" + ((i % 10) + 1)
            ));
        }
    }
}

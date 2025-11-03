package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Service.MedicalStaffAppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medical-staff-appointments")
public class MedicalStaffAppointmentWebController {
    private final MedicalStaffAppointmentService medicalStaffAppointmentService;

    public MedicalStaffAppointmentWebController(MedicalStaffAppointmentService medicalStaffAppointmentService) {
        this.medicalStaffAppointmentService = medicalStaffAppointmentService;
    }

    @GetMapping
    public String listMedicalStaffAppointments(Model model){
        model.addAttribute("assignments", medicalStaffAppointmentService.getAll());
        return "medical-staff-appointments/index";
    }

    @GetMapping("/new")
    public String showNewMedicalStaffAppointmentForm(Model model){
        model.addAttribute("assignment", new MedicalStaffAppointment("", "", ""));
        return "medical-staff-appointments/form";
    }

    @PostMapping
    public String createMedicalStaffAppointment(@ModelAttribute MedicalStaffAppointment assignment){
        medicalStaffAppointmentService.create(assignment);
        return "redirect:/medical-staff-appointments";
    }

    @PostMapping("/{id}/delete")
    public String deleteMedicalStaffAppointment(@PathVariable("id") String id){
        medicalStaffAppointmentService.remove(id);
        return "redirect:/medical-staff-appointments";
    }
}

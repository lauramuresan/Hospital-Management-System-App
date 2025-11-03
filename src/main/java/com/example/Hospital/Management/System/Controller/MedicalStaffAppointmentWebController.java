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
        model.addAttribute("medical staff appointment", medicalStaffAppointmentService.getAll());
        return "medical-staff-appointment/index";
    }
    @GetMapping("/new")
    public String showNewMedicalStaffAppointmentForm(Model model){
        model.addAttribute("medical staff appointment", new MedicalStaffAppointment("","",""));
        return "medical-staff-appointment/form";
    }
    @PostMapping
    public String createMedicalStaffAppointment(@ModelAttribute MedicalStaffAppointment medicalStaffAppointment){
        medicalStaffAppointmentService.create(medicalStaffAppointment);
        return "redirect:/medical-staff-appointment";
    }
    @PostMapping("/{id}/delete")
    public String deleteMedicalStaffAppointment(@PathVariable("id") String id){
        medicalStaffAppointmentService.remove(id);
        return "redirect:/medical-staff-appointment";
    }
}

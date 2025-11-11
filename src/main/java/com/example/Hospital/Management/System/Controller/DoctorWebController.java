package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Doctor;
import com.example.Hospital.Management.System.Service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorWebController extends GenericWebController<Doctor> {

    private final DoctorService doctorService;

    public DoctorWebController(DoctorService service) {
        super(service, "doctors", "doctor", "doctors");
        this.doctorService = service;
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/form";
    }

    // Override pentru edit să folosească același endpoint POST
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, @ModelAttribute Doctor doctor) {
        doctorService.create(doctor); // sau update dacă ai metodă separată
        return "redirect:/doctors";
    }
}
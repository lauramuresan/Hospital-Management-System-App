package com.example.Hospital.Management.System.Controller;
import com.example.Hospital.Management.System.Model.Doctor;
import com.example.Hospital.Management.System.Service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/doctors")
public class DoctorWebController {

    private final DoctorService doctorService;
    public DoctorWebController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAll());
        return "doctors/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("doctor", new Doctor("", "", "", new ArrayList<>(),"", " ", null));
        return "doctors/form";
    }
    @PostMapping
    public String createDoctor(@ModelAttribute Doctor doctor) {
        doctorService.create(doctor);
        return "redirect:/doctors";
    }
    @PostMapping("/{id}/delete")
    public String deleteDoctor(@PathVariable("id") String id) {
        doctorService.remove(id);
        return "redirect:/doctors";
    }
}

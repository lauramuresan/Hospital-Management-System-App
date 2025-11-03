package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Hospital;
import com.example.Hospital.Management.System.Service.HospitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/hospitals")
public class HospitalWebController {
    private final HospitalService hospitalService;

    public HospitalWebController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String listHospitals(Model model) {
        model.addAttribute("hospitals", hospitalService.getAll());
        return "hospital/index";
    }

    @GetMapping("/new")
    public String showHospitalForm(Model model) {
        model.addAttribute("appointment", new Hospital("", "", ""));
        return "hospital/form";
    }

    @PostMapping
    public String createHospital(@ModelAttribute Hospital hospital) {
        hospitalService.create(hospital);
        return "redirect:/hospitals";
    }

    @PostMapping("/{id}/delete")
    public String deleteHospital(@PathVariable("id") String id) {
        hospitalService.remove(id);
        return "redirect:/hospitals";
    }
}
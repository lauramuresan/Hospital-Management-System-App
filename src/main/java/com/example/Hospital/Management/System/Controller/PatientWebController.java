package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Patient;
import com.example.Hospital.Management.System.Service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/patients")
public class PatientWebController {

    private final PatientService patientService;

    public PatientWebController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAll());
        return "patients/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("patient", new Patient("", "", new ArrayList<>(), "", null));
        return "patients/form";
    }

    @PostMapping
    public String createPatient(@ModelAttribute Patient patient) {
        patientService.create(patient);
        return "redirect:/patients";
    }

    @PostMapping("/{id}/delete")
    public String deletePatient(@PathVariable("id") String id) {
        patientService.remove(id);
        return "redirect:/patients";
    }
}

package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/doctors")
public class DoctorWebController extends GenericWebController<Doctor> {

    private final DoctorService doctorService;

    public DoctorWebController(DoctorService service) {
        super(service, "doctors", "doctor", "doctors");
        this.doctorService = service;
    }

    @Override
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "staffName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return super.list(model, sortField, sortDir);
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/form";
    }
}
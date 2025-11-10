package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Doctor;
import com.example.Hospital.Management.System.Service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/doctors")
public class DoctorWebController extends GenericWebController<Doctor> {

    public DoctorWebController(DoctorService service) {
        super(service, "doctors", "doctor", "doctors");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("doctor", new Doctor("", "", "", new ArrayList<>(), "", "", null));
        return "doctors/form";
    }
}
package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Patient;
import com.example.Hospital.Management.System.Service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/patients")
public class PatientWebController extends GenericWebController<Patient> {

    public PatientWebController(PatientService service) {
        super(service, "patients");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("patient", new Patient("", "", new ArrayList<>(), "", null));
        return "patients/form";
    }
}

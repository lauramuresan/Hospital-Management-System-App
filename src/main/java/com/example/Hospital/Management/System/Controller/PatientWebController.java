package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/patients")
public class PatientWebController extends GenericWebController<Patient> {

    public PatientWebController(PatientService service) {
        super(service, "patients", "patient", "patients");
    }

    @Override
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "patientName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return super.list(model, sortField, sortDir);
    }


    @Override
    public String showForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/form";
    }
}
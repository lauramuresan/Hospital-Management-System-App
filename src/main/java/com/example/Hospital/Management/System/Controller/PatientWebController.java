package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.SearchCriteria.PatientSearchCriteria;
import com.example.Hospital.Management.System.Service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/patients")
public class PatientWebController extends GenericWebController<Patient> {

    private final PatientService patientService;

    public PatientWebController(PatientService service) {
        super(service, "patients", "patient", "patients");
        this.patientService = service;
    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "patientID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") PatientSearchCriteria searchCriteria) {

        model.addAttribute("patients", patientService.findAllFiltered(sortField, sortDir, searchCriteria));

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return viewPath + "/index";
    }

    @Override
    @GetMapping({"/new", "/create"})
    public String showForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/form";
    }
}
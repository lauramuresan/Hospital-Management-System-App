package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.Enums.MedicalSpecialty;
import com.example.Hospital.Management.System.SearchCriteria.DoctorSearchCriteria; // IMPORT
import com.example.Hospital.Management.System.Service.DepartmentService; // IMPORT
import com.example.Hospital.Management.System.Service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/doctors")
public class DoctorWebController extends GenericWebController<Doctor> {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    public DoctorWebController(DoctorService service, DepartmentService departmentService) {
        super(service, "doctors", "doctor", "doctors");
        this.doctorService = service;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "staffID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") DoctorSearchCriteria searchCriteria) {

        model.addAttribute("doctors", doctorService.findAllFiltered(sortField, sortDir, searchCriteria));

        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("specialties", MedicalSpecialty.values());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "doctors/index";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("specialties", MedicalSpecialty.values());
        return "doctors/form";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(String id, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        String view = super.editForm(id, model, redirectAttributes);
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("specialties", MedicalSpecialty.values());
        return view;
    }
}
package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Model.Enums.NurseLevelQualification;
import com.example.Hospital.Management.System.SearchCriteria.NurseSearchCriteria; // IMPORT
import com.example.Hospital.Management.System.Service.DepartmentService; // IMPORT
import com.example.Hospital.Management.System.Service.NurseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/nurses")
public class NurseWebController extends GenericWebController<Nurse> {

    private final NurseService nurseService;
    private final DepartmentService departmentService;

    public NurseWebController(NurseService service, DepartmentService departmentService) {
        super(service, "nurses", "nurse", "nurses");
        this.nurseService = service;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "staffID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") NurseSearchCriteria searchCriteria) {

        model.addAttribute("nurses", nurseService.findAllFiltered(sortField, sortDir, searchCriteria));

        // Date pentru dropdown-uri
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("qualifications", NurseLevelQualification.values());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "nurses/index";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("nurse", new Nurse());
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("qualifications", NurseLevelQualification.values());
        return "nurses/form";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(String id, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        String view = super.editForm(id, model, redirectAttributes);
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("qualifications", NurseLevelQualification.values());
        return view;
    }
}
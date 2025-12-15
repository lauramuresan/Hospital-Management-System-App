package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.SearchCriteria.DepartmentSearchCriteria; // IMPORT
import com.example.Hospital.Management.System.Service.DepartmentService;
import com.example.Hospital.Management.System.Service.HospitalService; // IMPORT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/departments")
public class DepartmentWebController extends GenericWebController<Department> {

    private final DepartmentService departmentService;
    private final HospitalService hospitalService; // Adaugat pentru Dropdown in Filtre

    public DepartmentWebController(DepartmentService service, HospitalService hospitalService) {
        super(service, "departments", "department", "departments");
        this.departmentService = service;
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "departmentID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") DepartmentSearchCriteria searchCriteria) {

        model.addAttribute("departments", departmentService.findAllFiltered(sortField, sortDir, searchCriteria));

        model.addAttribute("hospitals", hospitalService.getAll());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "departments/index";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("hospitals", hospitalService.getAll());
        return "departments/form";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(String id, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        String view = super.editForm(id, model, redirectAttributes);
        model.addAttribute("hospitals", hospitalService.getAll());
        return view;
    }
}
package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Model.GeneralModel.Hospital; // Adăugat pentru detalii
import com.example.Hospital.Management.System.SearchCriteria.DepartmentSearchCriteria;
import com.example.Hospital.Management.System.Service.DepartmentService;
import com.example.Hospital.Management.System.Service.HospitalService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departments")
public class DepartmentWebController extends GenericWebController<Department> {

    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    // private final MedicalStaffService medicalStaffService; <-- Eliminat

    // Constructorul revine la forma originală, apelând constructorul părintelui
    public DepartmentWebController(
            DepartmentService service,
            HospitalService hospitalService) {

        super(service, "departments", "department", "departments");

        this.departmentService = service;
        this.hospitalService = hospitalService;
        // this.medicalStaffService = medicalStaffService; <-- Eliminat
    }

    // 1. LISTARE ȘI FILTRARE (GET /departments)
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "departmentID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") DepartmentSearchCriteria searchCriteria) {

        // Logica de filtrare specifică
        model.addAttribute(listName, departmentService.findAllFiltered(sortField, sortDir, searchCriteria));

        // Adaugă lista de spitale pentru dropdown-ul de filtrare
        model.addAttribute("hospitals", hospitalService.getAll());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return viewPath + "/index";
    }

    // 2. AFIȘARE FORMULAR ADĂUGARE (GET /departments/new)
    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute(modelName, new Department());
        model.addAttribute("hospitals", hospitalService.getAll());
        return viewPath + "/form";
    }

    // 3. AFIȘARE FORMULAR EDITARE (GET /departments/{id}/edit)
    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        // Folosim @PathVariable("id") pentru a asigura maparea corectă
        String view = super.editForm(id, model, redirectAttributes);
        model.addAttribute("hospitals", hospitalService.getAll());
        return view;
    }

    // 4. METODA DE SALVARE (POST /departments) este moștenită.


    // 5. AFIȘARE DETALII (GET /departments/{id})
    // Suprascriem pentru a adăuga Detaliile Spitalului, ignorând Personalul Medical
    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Department department = departmentService.findById(id);
            if (department == null) {
                redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu a fost găsit.");
                return "redirect:/" + viewPath;
            }

            model.addAttribute(modelName, department);

            // Adaugă detalii Spital (HospitalDetails)
            Hospital hospitalDetails = hospitalService.findById(department.getHospitalID());
            model.addAttribute("hospitalDetails", hospitalDetails);

            // medicalStaffList a fost eliminat de aici, dar ar trebui eliminat și din details.html

            return viewPath + "/details";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu a fost găsit sau ID invalid: " + e.getMessage());
            return "redirect:/" + viewPath;
        }
    }

    // 6. METODA DE ȘTERGERE (POST /departments/{id}/delete) este moștenită.
}
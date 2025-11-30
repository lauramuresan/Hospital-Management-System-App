package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping; // Adăugați importul pentru GetMapping

@Controller
@RequestMapping("/departments")
public class DepartmentWebController extends GenericWebController<Department> {

    public DepartmentWebController(DepartmentService service) {
        super(service, "departments", "department", "departments");
    }

    // Metoda care afișează formularul pentru ADAUGARE și EDITARE
    @Override
    @GetMapping("/new") // Asigurați-vă că folosiți ruta corectă pentru formularul nou
    public String showForm(Model model) {
        // CORECȚIA: Folosim constructorul implicit (fără argumente)
        // pentru a ne asigura că departmentID (și hospitalID) sunt null.
        // Aceasta declanșează un INSERT corect în JPA.
        model.addAttribute("department", new Department());
        return "departments/form";
    }
}
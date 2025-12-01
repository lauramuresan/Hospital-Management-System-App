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

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("department", new Department());
        return "departments/form";
    }
}
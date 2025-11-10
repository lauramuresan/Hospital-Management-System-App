package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Department;
import com.example.Hospital.Management.System.Service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/departments")
public class DepartmentWebController extends GenericWebController<Department> {

    public DepartmentWebController(DepartmentService service) {
        super(service, "departments", "department", "departments");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("department", new Department("", "", ""));
        return "departments/form";
    }
}
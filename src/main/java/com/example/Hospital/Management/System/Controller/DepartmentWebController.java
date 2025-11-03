package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Department;
import com.example.Hospital.Management.System.Service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentWebController {

    private final DepartmentService departmentService;

    public DepartmentWebController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAll());
        return "departments/index";
    }

    @GetMapping("/new")
    public String showDepartmentForm(Model model) {
        model.addAttribute("department", new Department("", "", ""));
        return "departments/form";
    }

    @PostMapping
    public String createDepartment(@ModelAttribute Department department) {
        departmentService.create(department);
        return "redirect:/departments";
    }

    @PostMapping("/{id}/delete")
    public String deleteDepartment(@PathVariable("id") String id) {
        departmentService.remove(id);
        return "redirect:/departments";
    }
}

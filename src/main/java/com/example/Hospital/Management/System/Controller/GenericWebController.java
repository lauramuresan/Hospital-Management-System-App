package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Service.BaseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public abstract class GenericWebController<T> {

    protected final BaseService<T> service;
    protected final String viewPath;

    protected GenericWebController(BaseService<T> service, String viewPath) {
        this.service = service;
        this.viewPath = viewPath;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute(viewPath, service.getAll());
        return viewPath + "/index";
    }

    @GetMapping("/new")
    public abstract String showForm(Model model);

    @PostMapping
    public String create(@ModelAttribute T entity) {
        service.create(entity);
        return "redirect:/" + viewPath;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        service.remove(id);
        return "redirect:/" + viewPath;
    }
}

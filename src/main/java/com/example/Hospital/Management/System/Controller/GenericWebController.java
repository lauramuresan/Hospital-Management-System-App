package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Service.BaseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public abstract class GenericWebController<T> {

    protected final BaseService<T> service;
    protected final String viewPath;
    protected final String modelName;
    protected final String listName;

    protected GenericWebController(BaseService<T> service, String viewPath, String modelName, String listName) {
        this.service = service;
        this.viewPath = viewPath;
        this.modelName = modelName;
        this.listName = listName;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute(listName, service.getAll());
        return viewPath + "/index";
    }

    @GetMapping("/new")
    public abstract String showForm(Model model);

    @PostMapping
    public String create(@ModelAttribute T entity) {
        service.create(entity);
        return "redirect:/" + viewPath;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        T entity = service.getById(id);
        model.addAttribute(modelName, entity);
        return viewPath + "/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model) {
        T entity = service.getById(id);
        model.addAttribute(modelName, entity);
        return viewPath + "/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, @ModelAttribute T entity) {
        service.create(entity);
        return "redirect:/" + viewPath;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        service.remove(id);
        return "redirect:/" + viewPath;
    }
}
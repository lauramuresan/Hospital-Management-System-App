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
        try {
            service.create(entity);
            return "redirect:/" + viewPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/" + viewPath + "?error=true";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        try {
            T entity = service.getById(id);
            model.addAttribute(modelName, entity);
            return viewPath + "/details";
        } catch (Exception e) {
            return "redirect:/" + viewPath + "?not_found=true";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model) {
        try {
            T entity = service.getById(id);
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        } catch (Exception e) {
            return "redirect:/" + viewPath + "?not_found=true";
        }
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, @ModelAttribute T entity) {
        try {
            service.create(entity); // sau service.update(entity) dacă ai metodă separată
            return "redirect:/" + viewPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/" + viewPath + "?error=true";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        try {
            service.remove(id);
            return "redirect:/" + viewPath;
        } catch (Exception e) {
            return "redirect:/" + viewPath + "?error=true";
        }
    }
}
package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Service.BaseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Method;
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
    public String list(
            Model model,
            @RequestParam(defaultValue = "hospitalID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);

        model.addAttribute(listName, service.getAll(sort));

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");


        return viewPath + "/index";
    }

    @GetMapping("/new")
    public abstract String showForm(Model model);

    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute T entity,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        }
        String action = "salvat";
        try {
            Method getIdMethod = entity.getClass().getMethod(String.format("get%sID", modelName.substring(0, 1).toUpperCase() + modelName.substring(1)));
            Object idValue = getIdMethod.invoke(entity);

            if (idValue != null && !String.valueOf(idValue).isBlank()) {
                action = "actualizat";
            }
        } catch (Exception ignored) {
        }

        try {
            service.create(entity);
            redirectAttributes.addFlashAttribute("successMessage", modelName + " a fost " + action + " cu succes.");
            return "redirect:/" + viewPath;

        } catch (Exception e) {
            model.addAttribute(modelName, entity);
            model.addAttribute("globalError", "Eroare: " + e.getMessage());
            return viewPath + "/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            T entity = service.getById(id);
            if (entity == null) {
                redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu a fost găsit.");
                return "redirect:/" + viewPath;
            }
            model.addAttribute(modelName, entity);
            return viewPath + "/details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu a fost găsit sau ID invalid.");
            return "redirect:/" + viewPath;
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            T entity = service.getById(id);
            if (entity == null) {
                redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu poate fi editat: nu a fost găsit.");
                return "redirect:/" + viewPath;
            }
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu poate fi editat: ID invalid.");
            return "redirect:/" + viewPath;
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            service.remove(id);
            redirectAttributes.addFlashAttribute("successMessage", modelName + " a fost șters.");
            return "redirect:/" + viewPath;
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("errorMessage", "Eroare la ștergere: " + e.getMessage());
            return "redirect:/" + viewPath;
        }
    }
}
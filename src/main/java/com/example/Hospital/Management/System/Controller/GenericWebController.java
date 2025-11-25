package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Service.BaseService;
import jakarta.validation.Valid; // Necesar pentru validare
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Necesar pentru rezultatul validării
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Pentru mesaje flash

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
        return viewPath + "/index"; // Ex: patients/index.html
    }

    @GetMapping("/new")
    public abstract String showForm(Model model);

    @PostMapping
    public String create(@Valid @ModelAttribute T entity,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {


        if (result.hasErrors()) {
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        }

        try {
            service.create(entity);
            redirectAttributes.addFlashAttribute("successMessage", modelName + " a fost salvat cu succes.");
            return "redirect:/" + viewPath;
        } catch (Exception e) {
            model.addAttribute(modelName, entity); // Păstrăm datele
            model.addAttribute("globalError", "Eroare: " + e.getMessage());
            return viewPath + "/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            T entity = service.getById(id);
            model.addAttribute(modelName, entity);
            return viewPath + "/details"; // Ex: patients/details.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu a fost găsit.");
            return "redirect:/" + viewPath;
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            T entity = service.getById(id);
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu poate fi editat: nu a fost găsit.");
            return "redirect:/" + viewPath;
        }
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id,
                       @Valid @ModelAttribute T entity,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        }

        try {
            service.create(entity);
            redirectAttributes.addFlashAttribute("successMessage", modelName + " a fost actualizat cu succes.");
            return "redirect:/" + viewPath;
        } catch (Exception e) {
            model.addAttribute(modelName, entity);
            model.addAttribute("globalError", "Eroare de actualizare: " + e.getMessage());
            return viewPath + "/form";
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
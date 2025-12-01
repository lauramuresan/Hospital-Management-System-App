package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Service.BaseService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Method; // Import adăugat pentru reflexie (deducere ID)

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

    /**
     * Metoda unică de salvare/actualizare (@PostMapping).
     * Gestionează INSERT (dacă ID-ul este nul) și UPDATE (dacă ID-ul este populat).
     */
    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute T entity,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        }

        // Deducem dacă este o actualizare sau o creare înainte de a salva
        String action = "salvat";
        try {
            // Tentativă de a obține ID-ul pentru a determina acțiunea
            Method getIdMethod = entity.getClass().getMethod(String.format("get%sID", modelName.substring(0, 1).toUpperCase() + modelName.substring(1)));
            Object idValue = getIdMethod.invoke(entity);

            if (idValue != null && !String.valueOf(idValue).isBlank()) {
                action = "actualizat";
            }
        } catch (Exception ignored) {
            // Ignorăm erorile de reflexie dacă nu putem obține ID-ul. Presupunem 'salvat'/'actualizat'.
        }

        try {
            service.create(entity);
            redirectAttributes.addFlashAttribute("successMessage", modelName + " a fost " + action + " cu succes.");
            return "redirect:/" + viewPath;

        } catch (Exception e) {
            // EROARE DE VALIDARE BUSINESS (ex: unicitate, FK lipsă)
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

    // ❌ METODA @PostMapping("/{id}/edit") A FOST ȘTEARSĂ

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
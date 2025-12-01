package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Service.BaseService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional; // Import adăugat, deși nu e folosit direct aici, e o practică bună

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
     * Metoda unică de salvare/actualizare (@PostMapping)
     * Gestionează INSERT (dacă ID-ul este nul) și UPDATE (dacă ID-ul este populat).
     */
    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute T entity, // Am redenumit în createOrUpdate
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute(modelName, entity);
            return viewPath + "/form";
        }

        try {
            // service.create(entity) apelează save() în adaptor, care folosește jpaRepository.save(entity)
            service.create(entity);

            // Determină dacă a fost o actualizare sau o creare pe baza prezenței unui ID
            // (Presupunând că DTO-ul T are o metodă getID/getName sau că putem deduce din context)
            String action = "salvat";

            // ATENȚIE: Aici am folosi logica DTO-ului (ex: if (entity.getID() != null))
            // Dar din motive de generic, presupunem că salvarea a fost OK.

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
            model.addAttribute(modelName, entity);
            return viewPath + "/details";
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
            // Formularul va folosi ID-ul populat pentru a declanșa operația de UPDATE în @PostMapping de mai sus
            return viewPath + "/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu poate fi editat: nu a fost găsit.");
            return "redirect:/" + viewPath;
        }
    }

    // ❌ METODA @PostMapping("/{id}/edit") A FOST ȘTEARSĂ!
    // Logica ei a fost preluată de @PostMapping fără path (metoda createOrUpdate)

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
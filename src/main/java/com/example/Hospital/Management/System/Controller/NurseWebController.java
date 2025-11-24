package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Service.NurseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nurses")
public class NurseWebController extends GenericWebController<Nurse> {

    private final NurseService nurseService;

    public NurseWebController(NurseService service) {
        super(service, "nurses", "nurse", "nurses");
        this.nurseService = service;
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "nurses/form";
    }

    // Override pentru a trata corect edit-ul
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, @ModelAttribute Nurse nurse) {
        nurseService.create(nurse); // sau update dacă ai metodă separată
        return "redirect:/nurses";
    }
}
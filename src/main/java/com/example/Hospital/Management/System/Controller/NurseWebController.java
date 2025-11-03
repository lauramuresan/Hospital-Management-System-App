package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Nurse;
import com.example.Hospital.Management.System.Service.NurseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/nurses")
public class NurseWebController {
    private final NurseService nurseService;
    public NurseWebController(NurseService nurseService) {
        this.nurseService = nurseService;
    }
    @GetMapping
    public String listNurses(Model model) {
        model.addAttribute("nurses",nurseService.getAll());
        return "nurses/index";
    }
    @GetMapping("/new")
    public String showNurseForm(Model model) {
        model.addAttribute("nurse", new Nurse("","","",new ArrayList<>(),"",null));
        return "nurses/form";
    }
    @PostMapping
    public String createNurse(@ModelAttribute Nurse nurse) {
        nurseService.create(nurse);
        return "redirect:/nurses";
    }
    @PostMapping("/{id}/delete")
    public String removeNurse(@PathVariable("id") String id) {
        nurseService.remove(id);
        return "redirect:/nurses";
    }

}

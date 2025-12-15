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

    @Override
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "staffName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return super.list(model, sortField, sortDir);
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "nurses/form";
    }
}
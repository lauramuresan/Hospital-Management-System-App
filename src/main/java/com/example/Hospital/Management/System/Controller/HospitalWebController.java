package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Service.HospitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Import necesar

@Controller
@RequestMapping("/hospitals")
public class HospitalWebController extends GenericWebController<Hospital> {

    public HospitalWebController(HospitalService service) {
        super(service, "hospitals", "hospital", "hospitals");
    }

    @Override
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return super.list(model, sortField, sortDir);
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("hospital", new Hospital("", "", ""));
        return "hospitals/form";
    }
}
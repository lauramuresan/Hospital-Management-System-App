package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Nurse;
import com.example.Hospital.Management.System.Service.NurseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/nurses")
public class NurseWebController extends GenericWebController<Nurse> {

    public NurseWebController(NurseService service) {
        super(service, "nurses", "nurse", "nurses");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("nurse", new Nurse("", "", "", new ArrayList<>(), "", null));
        return "nurses/form";
    }
}

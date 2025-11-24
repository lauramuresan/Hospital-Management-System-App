package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Service.HospitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospitals")
public class HospitalWebController extends GenericWebController<Hospital> {

    public HospitalWebController(HospitalService service) {
        super(service, "hospitals", "hospital", "hospitals");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("hospital", new Hospital("", "", ""));
        return "hospitals/form";
    }
}
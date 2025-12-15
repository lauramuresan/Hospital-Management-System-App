package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.SearchCriteria.HospitalSearchCriteria;
import com.example.Hospital.Management.System.Service.HospitalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hospitals")
public class HospitalWebController extends GenericWebController<Hospital> {

    private final HospitalService hospitalService;

    public HospitalWebController(HospitalService service) {
        super(service, "hospitals", "hospital", "hospitals");
        this.hospitalService = service;
    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "hospitalID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") HospitalSearchCriteria searchCriteria) {

        model.addAttribute("hospitals", hospitalService.findAllFiltered(sortField, sortDir, searchCriteria));

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "hospitals/index";
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("hospital", new Hospital("", "", ""));
        return "hospitals/form";
    }
}
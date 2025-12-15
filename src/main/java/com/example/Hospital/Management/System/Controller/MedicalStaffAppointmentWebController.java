package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.SearchCriteria.MedicalStaffAppointmentSearchCriteria; // IMPORT
import com.example.Hospital.Management.System.Service.MedicalStaffAppointmentService;
import com.example.Hospital.Management.System.Service.AppointmentService;
import com.example.Hospital.Management.System.Service.DoctorService;
import com.example.Hospital.Management.System.Service.NurseService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/medical-staff-appointments")
public class MedicalStaffAppointmentWebController extends GenericWebController<MedicalStaffAppointment> {

    private final MedicalStaffAppointmentService msaService;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final NurseService nurseService;

    public MedicalStaffAppointmentWebController(
            MedicalStaffAppointmentService service,
            AppointmentService appointmentService,
            DoctorService doctorService,
            NurseService nurseService) {

        super(service, "medical-staff-appointments", "assignment", "medicalStaffAppointments");
        this.msaService = service;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.nurseService = nurseService;
    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "medicalStaffAppointmentID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") MedicalStaffAppointmentSearchCriteria searchCriteria) {

        model.addAttribute("medicalStaffAppointments", msaService.findAllFiltered(sortField, sortDir, searchCriteria));

        addDropdownsToModel(model);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "medical-staff-appointments/index";
    }

    private void addDropdownsToModel(Model model) {
        model.addAttribute("appointments", appointmentService.findAll());

        List<Object> allStaff = new ArrayList<>();
        allStaff.addAll(doctorService.findAll());
        allStaff.addAll(nurseService.findAll());
        model.addAttribute("medicalStaff", allStaff);
    }

    @Override
    @GetMapping({"/new", "/create"})
    public String showForm(Model model) {
        model.addAttribute(modelName, new MedicalStaffAppointment("", "", ""));
        addDropdownsToModel(model);
        return viewPath + "/form";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            MedicalStaffAppointment entity = service.getById(id);
            if (entity == null) {
                redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu poate fi editat: nu a fost găsit.");
                return "redirect:/" + viewPath;
            }
            model.addAttribute(modelName, entity);
            addDropdownsToModel(model);
            return viewPath + "/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu poate fi editat: ID invalid.");
            return "redirect:/" + viewPath;
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("assignment") MedicalStaffAppointment domain,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            addDropdownsToModel(model);
            return viewPath + "/form";
        }
        try {
            ((MedicalStaffAppointmentService) service).save(domain);
            String message = domain.getMedicalStaffAppointmentID() != null && !domain.getMedicalStaffAppointmentID().isEmpty()
                    ? "actualizată" : "salvată";
            redirectAttributes.addFlashAttribute("successMessage", "Alocare " + message + " cu succes!");
            return "redirect:/" + viewPath;
        } catch (RuntimeException e) {
            model.addAttribute("globalError", e.getMessage());
            addDropdownsToModel(model);
            return viewPath + "/form";
        }
    }
}
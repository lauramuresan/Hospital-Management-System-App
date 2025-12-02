package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
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

    // Serviciile injectate
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final NurseService nurseService;

    // Constructorul CORECTAT pentru Autowiring
    public MedicalStaffAppointmentWebController(
            MedicalStaffAppointmentService service,
            AppointmentService appointmentService,
            DoctorService doctorService,
            NurseService nurseService) {

        // Setează 'modelName' la "assignment"
        super(service, "medical-staff-appointments", "assignment", "medicalStaffAppointments");

        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.nurseService = nurseService;
    }

    /**
     * Adaugă listele de Programări, Doctori și Asistenți medicali în Model
     * pentru a popula dropdown-urile formularului.
     */
    private void addDropdownsToModel(Model model) {
        // 1. Programări (Appointments)
        // Presupune că findAll() din AppointmentService este public.
        model.addAttribute("appointments", appointmentService.findAll());

        // 2. Personal Medical (Doctors + Nurses consolidat)
        // Obiectele Doctor și Nurse moștenesc MedicalStaff,
        // permițând accesul la staffID și staffName.
        List<Object> allStaff = new ArrayList<>();
        allStaff.addAll(doctorService.findAll());
        allStaff.addAll(nurseService.findAll());

        // Numele "medicalStaff" este folosit în Thymeleaf
        model.addAttribute("medicalStaff", allStaff);
    }

    @Override
    @GetMapping({"/new", "/create"})
    public String showForm(Model model) {
        // 1. Adaugă obiectul "assignment" (MedicalStaffAppointment)
        model.addAttribute(modelName, new MedicalStaffAppointment("", "", ""));

        // 2. Adaugă listele dropdown necesare
        addDropdownsToModel(model);

        return viewPath + "/form";
    }

    // Metoda de Salvare/Update a alocării
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("assignment") MedicalStaffAppointment domain,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Dacă există erori de validare, re-populează listele înainte de a returna formularul
            addDropdownsToModel(model);
            return viewPath + "/form";
        }

        try {
            // Salvarea datelor prin serviciul injectat
            service.save(domain);
            redirectAttributes.addFlashAttribute("successMessage", "Alocare salvată cu succes!");
            return "redirect:/" + viewPath; // Redirecționează la lista de alocări

        } catch (RuntimeException e) {
            // Tratează erorile de runtime (ex: erori DB)
            model.addAttribute("globalError", e.getMessage());
            addDropdownsToModel(model); // Re-populează listele la eroare
            return viewPath + "/form";
        }
    }
}
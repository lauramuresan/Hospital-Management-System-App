package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.Enums.AppointmentStatus;
import com.example.Hospital.Management.System.Service.AppointmentService;
import com.example.Hospital.Management.System.Service.PatientService;
import com.example.Hospital.Management.System.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/appointments")
public class AppointmentWebController extends GenericWebController<Appointment> {

    private final PatientService patientService;
    private final RoomService roomService;

    public AppointmentWebController(AppointmentService appointmentService,
                                    PatientService patientService,
                                    RoomService roomService) {

        super(appointmentService, "appointments", "appointment", "appointments");

        this.patientService = patientService;
        this.roomService = roomService;
    }

    private void addDropdownsToModel(Model model) {
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("statuses", AppointmentStatus.values());
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        Appointment appointment = new Appointment("", "", "", "",
                LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0),
                AppointmentStatus.ACTIVE,
                new ArrayList<>());

        model.addAttribute(modelName, appointment);
        addDropdownsToModel(model);

        return viewPath + "/form";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            String view = super.editForm(id, model, redirectAttributes);
            addDropdownsToModel(model);
            return view;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu a fost găsit.");
            return "redirect:/" + viewPath;
        }
    }

    /**
     * 🟢 CORECȚIE MAPARE: Am mutat metoda la un path nou (/save-custom)
     * pentru a evita conflictul cu GenericWebController#createOrUpdate.
     */
    @PostMapping("/save-custom")
    public String save(@Valid @ModelAttribute("appointment") Appointment domain,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            addDropdownsToModel(model);
            return viewPath + "/form";
        }

        try {
            service.save(domain);

            redirectAttributes.addFlashAttribute("successMessage", modelName + " salvat cu succes!");
            return "redirect:/" + viewPath;

        } catch (RuntimeException e) {
            model.addAttribute("globalError", e.getMessage());
            addDropdownsToModel(model);
            return viewPath + "/form";

        } catch (Exception e) {
            model.addAttribute("globalError", "A apărut o eroare neașteptată: " + e.getMessage());
            addDropdownsToModel(model);
            return viewPath + "/form";
        }
    }


    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Appointment appointment = service.getById(id);

            Patient patient = patientService.findById(appointment.getPatientID());
            Room room = roomService.findById(appointment.getRoomID());

            model.addAttribute(modelName, appointment);
            model.addAttribute("patientDetails", patient);
            model.addAttribute("roomDetails", room);

            return viewPath + "/details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", modelName + " nu a fost găsit.");
            return "redirect:/" + viewPath;
        }
    }
}
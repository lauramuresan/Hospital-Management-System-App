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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    /**
     * ✅ REZOLVĂ EROAREA DE COMPILARE (Problema #2)
     * Implementează metoda abstractă 'showForm(Model)' pentru calea GET /appointments/new.
     * Inițializează un obiect 'Appointment' nou și îl adaugă sub numele 'appointment' în Model.
     */
    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        // Inițializează obiectul nou Appointment
        Appointment appointment = new Appointment("", "", "", "",
                LocalDateTime.now(),
                AppointmentStatus.ACTIVE,
                new ArrayList<>());

        // ADĂUGAREA OBIECTULUI ÎN MODEL ESTE CRITICĂ PENTRU THYMELEAF
        model.addAttribute(modelName, appointment); // modelName este 'appointment'

        // Adaugă listele necesare pentru dropdown-uri
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("statuses", AppointmentStatus.values());

        return viewPath + "/form";
    }

    /**
     * Suprascrie editForm pentru a adăuga listele necesare la editare.
     */
    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Preluăm entitatea prin metoda părintelui (care adaugă 'appointment' la Model)
            String view = super.editForm(id, model, redirectAttributes);

            // Adăugăm listele suplimentare pe model
            model.addAttribute("patients", patientService.findAll());
            model.addAttribute("rooms", roomService.findAll());
            model.addAttribute("statuses", AppointmentStatus.values());

            return view;
        } catch (Exception e) {
            // Eroarea este gestionată deja de metoda super
            return "redirect:/" + viewPath;
        }
    }


    /**
     * @Override: Suprascrie metoda details pentru a îmbogăți modelul.
     */
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
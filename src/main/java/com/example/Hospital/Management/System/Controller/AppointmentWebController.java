package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Appointment;
import org.springframework.ui.Model;
import com.example.Hospital.Management.System.Service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/appointments")
public class AppointmentWebController {
    private final AppointmentService appointmentService;
    public AppointmentWebController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAll());
        return "appointments/index";
    }

    @GetMapping("/new")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment("","","", "",null, new ArrayList<>()));
        return "appointments/form";
    }

    @PostMapping
    public String createAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.create(appointment);
        return "redirect:/appointments";
    }

    @PostMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable("id") String id) {
        appointmentService.remove(id);
        return "redirect:/appointments";
    }

}

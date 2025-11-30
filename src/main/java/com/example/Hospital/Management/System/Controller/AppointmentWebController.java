package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.Enums.AppointmentStatus;
import com.example.Hospital.Management.System.Service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequestMapping("/appointments")
public class AppointmentWebController extends GenericWebController<Appointment> {

    public AppointmentWebController(AppointmentService service) {
        super(service, "appointments", "appointment", "appointments");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("appointment", new Appointment("", "", "","", LocalDateTime.now(), AppointmentStatus.ACTIVE, new ArrayList<>()));
        return "appointments/form";
    }
}
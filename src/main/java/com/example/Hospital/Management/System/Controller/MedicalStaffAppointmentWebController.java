package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Service.MedicalStaffAppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medical-staff-appointments")
public class MedicalStaffAppointmentWebController extends GenericWebController<MedicalStaffAppointment> {

    public MedicalStaffAppointmentWebController(MedicalStaffAppointmentService service) {
        super(service, "medical-staff-appointments", "assignment", "medicalStaffAppointments");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("assignment", new MedicalStaffAppointment("", "", ""));
        return "medical-staff-appointments/form";
    }
}

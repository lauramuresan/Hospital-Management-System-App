package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Room;
import com.example.Hospital.Management.System.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/rooms")
public class RoomWebController extends GenericWebController<Room> {

    public RoomWebController(RoomService service) {
        super(service, "rooms");
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("room", new Room("", "", 0.0, "", null, new ArrayList<>()));
        return "rooms/form";
    }
}

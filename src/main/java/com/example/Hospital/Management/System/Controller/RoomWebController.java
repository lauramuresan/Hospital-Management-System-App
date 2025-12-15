package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/rooms")
public class RoomWebController extends GenericWebController<Room> {

    public RoomWebController(RoomService service) {
        super(service, "rooms", "room", "rooms");
    }

    @Override
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "number") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return super.list(model, sortField, sortDir);
    }

    @Override
    public String showForm(Model model) {
        model.addAttribute("room", new Room("", "", 0, "", null, new ArrayList<>()));
        return "rooms/form";
    }
}
package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.Room;
import com.example.Hospital.Management.System.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/rooms")
public class RoomWebController {

    private final RoomService roomService;

    public RoomWebController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAll());
        return "rooms/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("room", new Room("", "", 0.0,"",null,new ArrayList<>()));
        return "rooms/form";
    }

    @PostMapping
    public String createRoom(@ModelAttribute Room room) {
        roomService.create(room);
        return "redirect:/rooms";
    }

    @PostMapping("/{id}/delete")
    public String deleteRoom(@PathVariable("id") String id) {
        roomService.remove(id);
        return "redirect:/rooms";
    }
}
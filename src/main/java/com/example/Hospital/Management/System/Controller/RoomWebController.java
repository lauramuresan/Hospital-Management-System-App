package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.Enums.RoomAvailability;
import com.example.Hospital.Management.System.SearchCriteria.RoomSearchCriteria; // IMPORT
import com.example.Hospital.Management.System.Service.HospitalService; // IMPORT
import com.example.Hospital.Management.System.Service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/rooms")
public class RoomWebController extends GenericWebController<Room> {

    private final RoomService roomService;
    private final HospitalService hospitalService;

    public RoomWebController(RoomService service, HospitalService hospitalService) {
        super(service, "rooms", "room", "rooms");
        this.roomService = service;
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "roomID") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @ModelAttribute("searchCriteria") RoomSearchCriteria searchCriteria) {

        model.addAttribute("rooms", roomService.findAllFiltered(sortField, sortDir, searchCriteria));

        model.addAttribute("hospitals", hospitalService.getAll());
        model.addAttribute("statuses", RoomAvailability.values());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "rooms/index";
    }

    @Override
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("room", new Room("", "", 0, "", null, new ArrayList<>()));
        model.addAttribute("hospitals", hospitalService.getAll());
        model.addAttribute("statuses", RoomAvailability.values());
        return "rooms/form";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(String id, Model model, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        String view = super.editForm(id, model, redirectAttributes);
        model.addAttribute("hospitals", hospitalService.getAll());
        model.addAttribute("statuses", RoomAvailability.values());
        return view;
    }
}
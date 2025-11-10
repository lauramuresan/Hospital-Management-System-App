package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Repository.RepositoryMode;
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RepositoryChooserController {

    private final RepositoryModeHolder modeHolder;
    public RepositoryChooserController(RepositoryModeHolder modeHolder) { this.modeHolder = modeHolder; }

    @GetMapping({"/", "/choose-repo"})
    public String chooseForm(Model model) {
        model.addAttribute("currentMode", modeHolder.getMode());
        return "choose-repo";
    }

    @PostMapping("/choose-repo")
    public String setMode(@RequestParam("mode") String mode) {
        if ("INFILE".equalsIgnoreCase(mode)) modeHolder.setMode(RepositoryMode.INFILE);
        else modeHolder.setMode(RepositoryMode.INMEMORY);
        return "redirect:/";
    }
}

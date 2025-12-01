package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Repository.RepositoryMode;
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RepositoryChooserController {

    private final RepositoryModeHolder modeHolder;

    public RepositoryChooserController(RepositoryModeHolder modeHolder) {
        this.modeHolder = modeHolder;
    }
    @GetMapping({"/", "/HospitalManagementSystem"})
    public String chooseForm(Model model) {
        model.addAttribute("currentMode", modeHolder.getMode());
        return "choose-repo";
    }

    @PostMapping("/choose-repo")
    public String setMode(@RequestParam("mode") String mode, RedirectAttributes redirectAttributes) {
        RepositoryMode selectedMode = RepositoryMode.INMEMORY;

        if ("INFILE".equalsIgnoreCase(mode)) {
            selectedMode = RepositoryMode.INFILE;
        } else if ("INMEMORY".equalsIgnoreCase(mode)) {
            selectedMode = RepositoryMode.INMEMORY;
        } else if ("MYSQL".equalsIgnoreCase(mode)) {
            selectedMode = RepositoryMode.MYSQL;
        }

        modeHolder.setMode(selectedMode);
        redirectAttributes.addFlashAttribute("successMessage",
                "Modul de persistență a fost schimbat la " + selectedMode + ".");
        return "redirect:/";
    }
}
package com.example.Hospital.Management.System.Controller;

import com.example.Hospital.Management.System.Service.HospitalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Die Anwendung funktioniert!";
    }
}
package ua.info.m1.bomberman.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultationCompte {
    @GetMapping("/consulteCompte")
    public String home() {

        return "consultationCompte";
    }


}

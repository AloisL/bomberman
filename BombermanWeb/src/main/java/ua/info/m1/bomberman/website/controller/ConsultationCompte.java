package ua.info.m1.bomberman.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConsultationCompte {

    @GetMapping("/bomberman/consulteCompte")
    public String consultation() {
        return "consultationCompte";
    }
    
    @PostMapping("/bomberman/consulteCompte")
    public String modifier() {
        return "consultationCompte";
    }
    
    @PostMapping("/bomberman/supprimer")
    public String supprimer() {
        return "connect.html";
    }

}

package ua.info.m1.bomberman.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    @GetMapping("/registerNewCompte")
    public String home() {

        return "register";
    }
}

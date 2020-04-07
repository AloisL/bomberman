package ua.info.m1.bomberman.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ConnectController {

    @GetMapping("/connection")
    public String home() {

        return "connect";
    }



}

package ua.info.m1.bomberman.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoriqueController {

	 @GetMapping("/bomberman/historique")
	    public String historique() {
	        return "historique";
	    }
}

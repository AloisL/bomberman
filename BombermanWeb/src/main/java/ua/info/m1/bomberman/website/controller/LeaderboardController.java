package ua.info.m1.bomberman.website.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.info.m1.bomberman.entities.RatioGame;
import ua.info.m1.bomberman.entities.User;
import ua.info.m1.bomberman.repositories.RatioGameRepository;
import ua.info.m1.bomberman.repositories.UserRepository;

@Controller
public class LeaderboardController {

	
	@Autowired
    private RatioGameRepository ratioGameRepository;
	
    @GetMapping("/bomberman/leaderboard")
    public String leaderboard() {
        return "leaderboard";
    }
    
    @PostMapping("/bomberman/leaderboard")
    public String leaderboard(@RequestParam String username, @RequestParam String password, Model model) {
    	List<RatioGame> ratioGamesAll= ratioGameRepository.findAll();
    	
        if (ratioGamesAll.size() != 0 ) {
            model.addAttribute("list", "Connexion réussie");
            model.addAttribute("alert", "alert");
            return "consultationCompte";
        } else {
            model.addAttribute("msg_error", "Identifiants erronées");
            model.addAttribute("alert", "alert");
            return "connect";
        }
    }

}

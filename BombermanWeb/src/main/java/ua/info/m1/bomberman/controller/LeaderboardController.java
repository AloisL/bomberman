package ua.info.m1.bomberman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.info.m1.bomberman.model.entities.RatioGame;
import ua.info.m1.bomberman.model.repositories.RatioGameRepository;

import java.util.List;

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
        List<RatioGame> ratioGamesAll = ratioGameRepository.findAll();

        if (ratioGamesAll.size() != 0) {
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

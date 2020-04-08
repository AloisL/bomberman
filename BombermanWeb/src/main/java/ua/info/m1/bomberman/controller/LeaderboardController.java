package ua.info.m1.bomberman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;
import ua.info.m1.bomberman.model.entities.RatioGame;
import ua.info.m1.bomberman.model.entities.User;
import ua.info.m1.bomberman.model.repositories.RatioGameRepository;
import ua.info.m1.bomberman.model.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LeaderboardController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatioGameRepository ratioGameRepository;

    @GetMapping("/bomberman/leaderboard")
    public String leaderboard(HttpServletRequest request) {
        Cookie cookieToken = WebUtils.getCookie(request, "session");
        String token = cookieToken.getValue();
        User user = userRepository.findUserByCurrentToken(token);
        if (user != null) return "leaderboard";
        else return "connect";
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

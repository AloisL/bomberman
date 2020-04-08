package ua.info.m1.bomberman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import java.util.List;

@Controller
public class LeaderboardController {




    @GetMapping("/bomberman/leaderboard")
    public String leaderboard() {
        return "leaderboard";
    }

    @PostMapping("/bomberman/leaderboard")
    public String leaderboard(@RequestParam String username, @RequestParam String password, Model model) {
            return "connect";
      
    }

}

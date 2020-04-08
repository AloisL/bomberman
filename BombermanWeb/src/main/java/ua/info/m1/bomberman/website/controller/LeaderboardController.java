package ua.info.m1.bomberman.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LeaderboardController {

    @GetMapping("/bomberman/leaderboard")
    public String leaderboard() {
        return "leaderboard";
    }

}

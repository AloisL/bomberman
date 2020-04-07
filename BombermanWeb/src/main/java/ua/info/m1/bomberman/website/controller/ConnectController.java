package ua.info.m1.bomberman.website.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.info.m1.bomberman.entities.User;
import ua.info.m1.bomberman.repositories.UserRepository;


@Controller
public class ConnectController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bomberman/connection")
    public String home() {
        return "connect";
    }

    @PostMapping("/bomberman/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,Model model ) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            userRepository.save(user);
            return "consultationCompte";
        } else {
        	model.addAttribute("msg_error","Identifiants erronées");
        	model.addAttribute("alert","alert");
        	
            return "connect";
        }
    }

}

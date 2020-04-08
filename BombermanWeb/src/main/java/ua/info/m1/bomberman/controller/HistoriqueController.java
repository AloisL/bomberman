package ua.info.m1.bomberman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;
import ua.info.m1.bomberman.model.entities.User;
import ua.info.m1.bomberman.model.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HistoriqueController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bomberman/historique")
    public String historique(HttpServletRequest request) {
        Cookie cookieToken = WebUtils.getCookie(request, "session");
        String token = cookieToken.getValue();
        User user = userRepository.findUserByCurrentToken(token);
        if (user != null) return "historique";
        else return "connect";
    }
}

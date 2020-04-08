package ua.info.m1.bomberman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;
import ua.info.m1.bomberman.model.entities.User;
import ua.info.m1.bomberman.model.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bomberman/connection")
    public String home() {
        return "connect";
    }

    @PostMapping("/bomberman/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("msg_success", "Connexion réussie");
            model.addAttribute("alert", "alert");
            String token = generateToken();
            user.setCurrentToken(token);
            userRepository.save(user);
            response.addCookie(new Cookie("session", token));
            return "consultationCompte";
        } else {
            model.addAttribute("msg_error", "Identifiants erronées");
            model.addAttribute("alert", "alert");
            return "connect";
        }
    }

    /**
     * Génère un token de connection aléatoire sur 24 charactères
     *
     * @return random 24 char token
     */
    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @PostMapping("/bomberman/deconnection")
    public String disconnect(Model model, HttpServletRequest request, HttpServletResponse response) {
        Cookie sessionCookie = WebUtils.getCookie(request, "session");
        String token = sessionCookie.getValue();
        User user = userRepository.findUserByCurrentToken(token);
        user.setCurrentToken(token);
        userRepository.save(user);
        response.addCookie(new Cookie("session", ""));
        model.addAttribute("msg_error", "Déconnecté");
        model.addAttribute("alert", "alert");
        return "connect";
    }

}

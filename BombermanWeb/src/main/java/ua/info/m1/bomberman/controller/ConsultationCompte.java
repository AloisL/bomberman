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

@Controller
public class ConsultationCompte {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bomberman/consulteCompte")
    public String consultation(HttpServletRequest request) {
        Cookie cookieToken = WebUtils.getCookie(request, "session");
        String token = cookieToken.getValue();
        User user = userRepository.findUserByCurrentToken(token);
        if (user != null) return "consultationCompte";
        else return "connect";
    }

    @PostMapping(value = "/bomberman/gestionCompte")
    public String supprimer(@RequestParam String action, @RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        if (action.equals("Supprimer")) {
            Cookie cookieToken = WebUtils.getCookie(request, "session");
            String token = cookieToken.getValue();
            User user = userRepository.findUserByCurrentToken(token);
            if (user != null) userRepository.delete(user);
            model.addAttribute("msg_error", "Compte supprimé");
            model.addAttribute("alert", "alert");
            return "connect";
        } else {
            Cookie cookieToken = WebUtils.getCookie(request, "session");
            String token = cookieToken.getValue();
            User user = userRepository.findUserByCurrentToken(token);
            //if (user != null) userRepository.delete(user);
            model.addAttribute("msg_success", "Compte modifié");
            model.addAttribute("alert", "alert");
            return "consultationCompte";
        }
    }

}

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
    public String consultation(HttpServletRequest request, Model model) {
        Cookie cookieToken = WebUtils.getCookie(request, "session");
        String token = cookieToken.getValue();
        User user = userRepository.findUserByCurrentToken(token);
        if (user != null) {
            model.addAttribute("user", user);
            return "consultationCompte";
        } else return "connect";
    }

    @PostMapping(value = "/bomberman/gestionCompte")
    public String supprimer(@RequestParam String action, @RequestParam String username, @RequestParam String password, @RequestParam String passwordvalid, @RequestParam String mail, Model model, HttpServletRequest request) {
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
            model.addAttribute("user", user);
            if (user != null) {
                if (!username.isEmpty())
                    if (userRepository.findByUsername(username) == null)
                        user.setUsername(username);
                    else {
                        model.addAttribute("msg_error", "L'identifiant utilisateur est déjà existant'");
                        model.addAttribute("alert", "alert");
                        return "consultationCompte";
                    }

                if (!password.isEmpty() && password.equals(passwordvalid))
                    user.setPassword(password);
                else {
                    model.addAttribute("msg_error", "Les mots de passe ne coincident pas");
                    model.addAttribute("alert", "alert");
                    return "consultationCompte";
                }

                if (!mail.isEmpty())
                    user.setEmail(mail);

            }
            try {
                userRepository.save(user);
                model.addAttribute("msg_success", "Compte modifié");
                model.addAttribute("alert", "alert");
            } catch (Exception e) {
                model.addAttribute("msg_error", e.getMessage());
                model.addAttribute("alert", "alert");
            }
            return "consultationCompte";
        }
    }

}

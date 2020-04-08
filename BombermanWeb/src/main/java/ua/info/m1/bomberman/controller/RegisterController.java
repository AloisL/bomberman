package ua.info.m1.bomberman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.info.m1.bomberman.model.entities.User;
import ua.info.m1.bomberman.model.repositories.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bomberman/register")
    public String home() {
        return "register";
    }

    /**
     * Créer un utilisateur
     *
     * @return 200/401 token/"Bad Creditentials"
     */
    @PostMapping(path = "/bomberman/registernew")
    String register(@RequestParam String username, @RequestParam String password, @RequestParam String mail, Model model) {
        User user = userRepository.findByUsername(username);
        // Si utilisateur inexistant
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(mail);
            userRepository.save(user);
            model.addAttribute("msg_success", "Inscription réussie");
            model.addAttribute("alert", "alert");
            return "connect";
        } else {
            model.addAttribute("msg_error", "Inscription échouée");
            model.addAttribute("alert", "alert");
            return "register";
        }
    }

}

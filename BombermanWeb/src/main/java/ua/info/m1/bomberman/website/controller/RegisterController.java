package ua.info.m1.bomberman.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.info.m1.bomberman.entities.User;
import ua.info.m1.bomberman.repositories.UserRepository;

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
    public @ResponseBody
    ResponseEntity createAccount(@RequestParam String username, @RequestParam String password, @RequestParam String mail) {
        User user = userRepository.findByUsername(username);
        // Si utilisateur inexistant
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(mail);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Compte créé");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Création compte refusée");
        }
    }

}

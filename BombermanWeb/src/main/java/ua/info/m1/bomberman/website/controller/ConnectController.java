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
public class ConnectController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/connection")
    public String home() {
        return "connect";
    }

    @PostMapping("/login")
    public @ResponseBody
    ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("page consultation compte");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("page conenction refusee");
        }
    }

}

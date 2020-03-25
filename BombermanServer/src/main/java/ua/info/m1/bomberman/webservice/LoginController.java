package ua.info.m1.bomberman.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.info.m1.bomberman.entities.User;
import ua.info.m1.bomberman.repositories.UserRepository;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/bomberman/login")
    public @ResponseBody
    String login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)){
            String token = generateToken();
            user.setCurrentToken(token);
            userRepository.save(user);
            return generateToken();
        } else return "";
    }

    private String generateToken()  {
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}

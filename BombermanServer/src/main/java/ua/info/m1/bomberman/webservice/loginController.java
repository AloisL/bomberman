package ua.info.m1.bomberman.webservice;

import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class loginController {

    @GetMapping(path = "/bomberman/login")
    public @ResponseBody
    String login(@RequestParam String login, @RequestParam String password) {
        if (login.equals("bomberman") && password.equals("bomberman")) {
            // Génération du token
            SecureRandom secureRandom = new SecureRandom(); //threadsafe
            Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
            byte[] randomBytes = new byte[24];
            secureRandom.nextBytes(randomBytes);
            return base64Encoder.encodeToString(randomBytes);
        } else return "";
    }
}

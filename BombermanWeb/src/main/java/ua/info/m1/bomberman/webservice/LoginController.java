package ua.info.m1.bomberman.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.info.m1.bomberman.entities.User;
import ua.info.m1.bomberman.repositories.UserRepository;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Si l'utilisateur est trouvé et le mot de passe est correct
     * Alors génère un token et l'ajoute au User puis retourne un code 200 contenant le token
     * Sinon retourne un code 401
     *
     * @return 200/401 token/"Bad Creditentials"
     */
    @GetMapping(path = "/bomberman/api/login")
    public @ResponseBody
    ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            String token = generateToken();
            user.setCurrentToken(token);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad creditentials");
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
}

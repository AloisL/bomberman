package ua.info.m1.bomberman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.info.m1.bomberman.model.entities.HistoriqueGame;
import ua.info.m1.bomberman.model.entities.User;
import ua.info.m1.bomberman.model.repositories.HistoriqueGameRepository;
import ua.info.m1.bomberman.model.repositories.UserRepository;

@RestController
public class GameHistoryApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private HistoriqueGameRepository historiqueGameRepository;

    @PostMapping(path = "/bomberman/api/addHistory")
    ResponseEntity addHistory(@RequestParam String bombermanToken, @RequestParam String username,
                              @RequestParam String victoire, @RequestParam String dateDebut,
                              @RequestParam String dateFin) {

        HistoriqueGame historiqueGame = new HistoriqueGame();
        try {
            User admin = userRepository.findUserByCurrentToken(bombermanToken);
            // Si le token correspond à l'administrateur alors on ajoute les stats
            if (admin.getUsername().equals("bomberman")) {
                User user = userRepository.findByUsername(username);
                historiqueGame.setIdUser(user.getId());
                historiqueGame.setVictoire(victoire);
                historiqueGame.setDateDebut(dateDebut);
                historiqueGame.setDateFin(dateFin);
                historiqueGameRepository.save(historiqueGame);
                return ResponseEntity.status(HttpStatus.OK).body("");
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

}

package ua.info.m1.bomberman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;
import ua.info.m1.bomberman.model.entities.HistoriqueGame;
import ua.info.m1.bomberman.model.entities.User;
import ua.info.m1.bomberman.model.repositories.HistoriqueGameRepository;
import ua.info.m1.bomberman.model.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HistoriqueController {

    @Autowired
    private HistoriqueGameRepository historiqueGameRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bomberman/historique")
    public String historique(HttpServletRequest request, Model model) {
        Cookie cookieToken = WebUtils.getCookie(request, "session");
        String token = cookieToken.getValue();
        User user = userRepository.findUserByCurrentToken(token);
        if (user != null) {
            Long idLong = userRepository.findByUsername("Tom").getId();

            HistoriqueGame g1 = new HistoriqueGame();
            g1.setVictoire("true");
            g1.setDateFin("02/04/2020");
            g1.setIdUser(idLong);
            HistoriqueGame g2 = new HistoriqueGame();
            g2.setVictoire("false");
            g2.setDateFin("04/04/2020");
            g2.setIdUser(idLong);
            HistoriqueGame g3 = new HistoriqueGame();
            g3.setVictoire("true");
            g3.setDateFin("06/04/2020");
            g3.setIdUser(idLong);
            historiqueGameRepository.save(g1);
            historiqueGameRepository.save(g2);
            historiqueGameRepository.save(g3);

            List<HistoriqueGame> games = historiqueGameRepository.findAll();
            model.addAttribute("games", games);
            return "historique";
        } else return "connect";
    }
}

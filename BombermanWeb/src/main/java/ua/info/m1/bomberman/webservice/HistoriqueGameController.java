package ua.info.m1.bomberman.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ua.info.m1.bomberman.model.repositories.HistoriqueGameRepository;

@RestController
public class HistoriqueGameController {

	 @Autowired
	    private HistoriqueGameRepository historiqueGameRepository;
}
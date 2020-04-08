package ua.info.m1.bomberman.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ua.info.m1.bomberman.model.entities.HistoriqueGame;

public interface HistoriqueGameRepository extends CrudRepository<HistoriqueGame, Integer> {

    List<HistoriqueGame> findAll();
}

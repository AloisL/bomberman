package ua.info.m1.bomberman.repositories;

import org.springframework.data.repository.CrudRepository;

import ua.info.m1.bomberman.entities.RatioGame;
import ua.info.m1.bomberman.entities.User;

public interface RatioGameRepository extends CrudRepository<RatioGame, Integer> {

    User findByUsername(String login);
    Iterable<RatioGame> findAll();

}
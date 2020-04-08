package ua.info.m1.bomberman.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ua.info.m1.bomberman.entities.RatioGame;
import ua.info.m1.bomberman.entities.User;

public interface RatioGameRepository extends CrudRepository<RatioGame, Integer> {

    List<RatioGame> findAll();

    @Query("from RatioGame  order by defaite asc")
    public List<RatioGame> allRatioBydefaiteASC(); // plus petit au plus grand
    
    @Query("from RatioGame  order by defaite")
    public List<RatioGame> allRatioBydefaite(); //plus grand au plus petit
    
    @Query("from RatioGame  order by victoire")
    public List<RatioGame> allRatioByVictoire(); //plus grand au plus petit 
    
    @Query("from RatioGame  order by victoire asc")
    public List<RatioGame> allRatioByVictoireASC(); // plus petit au plus grand
}
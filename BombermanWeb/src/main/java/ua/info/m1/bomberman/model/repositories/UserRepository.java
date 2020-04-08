package ua.info.m1.bomberman.model.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ua.info.m1.bomberman.model.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	List<User> findAll();

    User findByUsername(String login);

    User findUserByCurrentToken(String token);

}

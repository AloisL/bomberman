package ua.info.m1.bomberman.model.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.info.m1.bomberman.model.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String login);

}

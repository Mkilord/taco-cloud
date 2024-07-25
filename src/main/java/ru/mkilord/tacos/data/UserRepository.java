package ru.mkilord.tacos.data;

import org.springframework.data.repository.CrudRepository;
import ru.mkilord.tacos.entites.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

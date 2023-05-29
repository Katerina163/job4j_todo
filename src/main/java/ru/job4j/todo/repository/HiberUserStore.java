package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HiberUserStore implements UserStore {
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User where login = :fLogin and password = :fPassword",
                User.class, Map.of("fLogin", login, "fPassword", password));
    }

    @Override
    public void add(User user) {
        crudRepository.run(session -> session.save(user));
    }

    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                "from User where id = :fId",
                User.class, Map.of("fId", id));
    }
}

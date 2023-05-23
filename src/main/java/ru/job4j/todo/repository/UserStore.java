package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserStore {
    Optional<User> findById(int id);

    Optional<User> findByLoginAndPassword(String login, String password);

    void add(User user);
}

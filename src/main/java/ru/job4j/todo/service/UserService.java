package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLogin(String login);

    boolean add(User user);
}

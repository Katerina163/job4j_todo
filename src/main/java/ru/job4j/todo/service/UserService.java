package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(int id);

    boolean add(User user);
}

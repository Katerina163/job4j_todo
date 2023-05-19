package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserStore;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {
    private UserStore store;

    public SimpleUserService(UserStore hiberUserStore) {
        store = hiberUserStore;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return store.findByLogin(login);
    }

    @Override
    public boolean add(User user) {
        return store.add(user);
    }
}
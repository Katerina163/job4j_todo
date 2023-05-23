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
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findByLoginAndPassword(login, password);
    }

    @Override
    public void add(User user) {
        store.add(user);
    }
}

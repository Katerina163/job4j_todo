package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HiberUserStore implements UserStore {
    private final CrudRepository crudRepository;
    private final SessionFactory sf;

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
        Transaction tr = null;
        Optional<User> user = Optional.empty();
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            user = Optional.of(session.get(User.class, id));
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }
}

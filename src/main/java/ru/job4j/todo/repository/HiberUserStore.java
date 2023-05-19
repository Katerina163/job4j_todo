package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HiberUserStore implements UserStore {
    private final SessionFactory sf;

    @Override
    public Optional<User> findByLogin(String login) {
        Transaction tr = null;
        Optional<User> user = Optional.empty();
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            user = session.createQuery(
                            "from User where login = :fLogin", User.class)
                    .setParameter("fLogin", login)
                    .uniqueResultOptional();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            session.save(user);
            result = true;
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
                result = false;
            }
            e.printStackTrace();
        }
        return result;
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

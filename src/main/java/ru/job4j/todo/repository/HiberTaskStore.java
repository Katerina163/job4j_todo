package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HiberTaskStore implements TaskStore {
    private final SessionFactory sf;

    @Override
    public Collection<Task> findAll() {
        Transaction tr = null;
        Collection<Task> result = Collections.emptyList();
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            result = session.createQuery("from Task order by created", Task.class).list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Task> findAllNew() {
        Transaction tr = null;
        Collection<Task> result = Collections.emptyList();
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            result = session.createQuery("from Task where created between :fStart and :fEnd order by created", Task.class)
                    .setParameter("fStart", LocalDateTime.now().minusHours(24))
                    .setParameter("fEnd", LocalDateTime.now())
                    .list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Task> findAllDone() {
        Transaction tr = null;
        Collection<Task> result = Collections.emptyList();
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            result = session.createQuery("from Task where done = true order by created", Task.class).list();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Transaction tr = null;
        Optional<Task> result = Optional.empty();
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            result = Optional.ofNullable(session.get(Task.class, id));
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            Task task = new Task();
            task.setId(id);
            session.delete(task);
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
    public boolean update(Task task) {
        boolean result = false;
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            session.update(task);
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
    public boolean doneById(int id, boolean done) {
        boolean result = false;
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            session.createQuery("update Task set done = :fDone where id = :fId")
                    .setParameter("fDone", done)
                    .setParameter("fId", id)
                    .executeUpdate();
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
    public Task add(Task task) {
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            session.save(task);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return task;
    }
}
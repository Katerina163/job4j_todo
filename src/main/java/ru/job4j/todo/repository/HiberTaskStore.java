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
                    .setParameter("fStart", LocalDateTime.now().minusHours(12))
                    .setParameter("fEnd", LocalDateTime.now().plusHours(12))
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
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            Task task = new Task();
            task.setId(id);
            session.delete(task);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return findById(id).isEmpty();
    }

    @Override
    public boolean update(Task task) {
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            session.update(task);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return task.equals(findById(task.getId()).get());
    }

    @Override
    public boolean doneById(int id, boolean done) {
        Transaction tr = null;
        try (Session session = sf.openSession()) {
            tr = session.beginTransaction();
            session.createQuery("update Task set done = :fDone where id = :fId")
                    .setParameter("fDone", done);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return findById(id).get().isDone() == done;
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
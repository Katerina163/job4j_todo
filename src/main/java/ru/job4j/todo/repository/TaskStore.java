package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskStore {
    Collection<Task> findAll();

    Collection<Task> findAllNew();

    Collection<Task> findAllDone();

    Optional<Task> findById(int id);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean doneById(int id, boolean done);

    Task add(Task task);
}

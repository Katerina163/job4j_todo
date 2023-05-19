package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Collection<Task> findAll();

    Collection<Task> findAllNew();

    Collection<Task> findAllDone();

    Optional<Task> findById(int id);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean doneById(int id, boolean done);

    Optional<Task> add(Task task);
}

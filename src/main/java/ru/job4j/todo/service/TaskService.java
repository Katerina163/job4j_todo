package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Collection<Task> findAll();

    Collection<Task> findAllNew();

    Collection<Task> findAllDone();

    Optional<Task> findById(int id);

    void deleteById(int id);

    void update(Task task);

    void doneById(int id, boolean done);

    Optional<Task> add(Task task);
}

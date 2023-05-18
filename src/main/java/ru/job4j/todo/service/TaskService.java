package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDTO;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Collection<TaskDTO> findAll();

    Collection<TaskDTO> findAllNew();

    Collection<TaskDTO> findAllDone();

    Optional<TaskDTO> findById(int id);

    boolean deleteById(int id);

    boolean update(TaskDTO task);

    boolean doneById(int id, boolean done);

    Optional<Task> add(TaskDTO task);
}

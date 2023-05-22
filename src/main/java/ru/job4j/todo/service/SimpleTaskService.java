package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTaskService implements TaskService {
    private TaskStore store;

    public SimpleTaskService(TaskStore hiberTaskStore) {
        store = hiberTaskStore;
    }

    @Override
    public Collection<Task> findAll() {
        return store.findAll();
    }

    @Override
    public Collection<Task> findAllNew() {
        return store.findAllNew();
    }

    @Override
    public Collection<Task> findAllDone() {
        return store.findAllDone();
    }

    @Override
    public Optional<Task> findById(int id) {
        return store.findById(id);
    }

    @Override
    public void deleteById(int id) {
        store.deleteById(id);
    }

    @Override
    public void update(Task task) {
        store.update(task);
    }

    @Override
    public void doneById(int id, boolean done) {
        store.doneById(id, done);
    }

    @Override
    public Optional<Task> add(Task task) {
        return Optional.ofNullable(store.add(task));
    }
}

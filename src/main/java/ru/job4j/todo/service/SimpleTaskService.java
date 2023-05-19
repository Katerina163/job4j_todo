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
    public boolean deleteById(int id) {
        return store.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return store.update(task);
    }

    @Override
    public boolean doneById(int id, boolean done) {
        return store.doneById(id, done);
    }

    @Override
    public Optional<Task> add(Task task) {
        return Optional.ofNullable(store.add(task));
    }
/*
    private Task formatDTO(TaskDTO task) {
        Task result = new Task();
        result.setId(task.getId());
        result.setName(task.getName());
        result.setDescription(task.getDescription());
        String[] arrayStr = task.getCreated().split("[/: ]");
        int[] arrayInt = new int[arrayStr.length];
        for (int i = 0; i < arrayStr.length; i++) {
            arrayInt[i] = Integer.parseInt(arrayStr[i]);
        }
        result.setCreated(LocalDateTime.of(arrayInt[2], arrayInt[1], arrayInt[0], arrayInt[3], arrayInt[4]));
        result.setDone(task.isDone());
        return result;
    }*/
}

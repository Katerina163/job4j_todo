package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDTO;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTaskService implements TaskService {
    private TaskStore store;

    public SimpleTaskService(TaskStore hiberTaskStore) {
        store = hiberTaskStore;
    }

    @Override
    public Collection<TaskDTO> findAll() {
        return store.findAll().stream().map(this::format).toList();
    }

    @Override
    public Collection<TaskDTO> findAllNew() {
        return store.findAllNew().stream().map(this::format).toList();
    }

    @Override
    public Collection<TaskDTO> findAllDone() {
        return store.findAllDone().stream().map(this::format).toList();
    }

    @Override
    public Optional<TaskDTO> findById(int id) {
        return store.findById(id).map(this::format);
    }

    @Override
    public boolean deleteById(int id) {
        return store.deleteById(id);
    }

    @Override
    public boolean update(TaskDTO task) {
        return store.update(formatDTO(task));
    }

    @Override
    public boolean doneById(int id, boolean done) {
        return store.doneById(id, done);
    }

    @Override
    public Optional<Task> add(TaskDTO task) {
        return Optional.ofNullable(store.add(formatDTO(task)));
    }

    private TaskDTO format(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setCreated(task.getCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        dto.setDone(task.isDone());
        return dto;
    }

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
    }
}

package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HiberTaskStore implements TaskStore {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query(
                "select distinct t from Task t left join fetch t.priority left join fetch t.categories order by created",
                Task.class);
    }

    @Override
    public Collection<Task> findAllNew() {
        String s = "select distinct t from Task t left join fetch t.priority left join fetch"
                + " t.categories where created between :fStart and :fEnd order by created";
        return crudRepository.query(s, Task.class, Map.of(
                "fStart", LocalDateTime.now().minusHours(24),
                "fEnd", LocalDateTime.now()));
    }

    @Override
    public Collection<Task> findAllDone() {
        String s = "select distinct t from Task t left join fetch t.priority "
                + "left join fetch t.categories where done = true order by created";
        return crudRepository.query(s, Task.class);
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "select distinct t from Task t left join fetch t.priority left join fetch t.categories where t.id = :fId",
                Task.class, Map.of("fId", id));
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "delete from Task where id = :fId", Map.of("fId", id));
    }

    @Override
    public void update(Task task) {
        crudRepository.run(session -> session.update(task));
    }

    @Override
    public void doneById(int id, boolean done) {
        crudRepository.run("update Task set done = :fDone where id = :fId",
                Map.of("fDone", done, "fId", id));
    }

    @Override
    public Task add(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }
}
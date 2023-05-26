package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HiberCategoryStore implements CategoryStore {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                "from Category where id = :fId",
                Category.class, Map.of("fId", id));
    }
}

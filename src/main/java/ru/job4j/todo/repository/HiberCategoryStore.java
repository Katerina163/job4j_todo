package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;

@AllArgsConstructor
@Repository
public class HiberCategoryStore implements CategoryStore {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }
}

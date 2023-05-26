package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleCategoryService implements CategoryService {
    private CategoryStore store;

    public SimpleCategoryService(CategoryStore hiberCategoryStore) {
        store = hiberCategoryStore;
    }

    @Override
    public Collection<Category> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return store.findById(id);
    }
}

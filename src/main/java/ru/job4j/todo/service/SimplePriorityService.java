package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityStore;

import java.util.Collection;

@Service
public class SimplePriorityService implements PriorityService {
    private PriorityStore store;

    public SimplePriorityService(PriorityStore hiberPriorityRepository) {
        store = hiberPriorityRepository;
    }

    @Override
    public Collection<Priority> findAll() {
        return store.findAll();
    }
}

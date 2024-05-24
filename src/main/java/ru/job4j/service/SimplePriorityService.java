package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Priority;
import ru.job4j.repository.PriorityRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {
    private final PriorityRepository repository;

    @Override
    public Collection<Priority> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Priority> findById(int id) {
        return repository.findById(id);
    }
}

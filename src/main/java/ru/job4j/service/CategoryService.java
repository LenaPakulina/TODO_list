package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Category;
import ru.job4j.repository.CategoryRepository;

import java.util.Collection;
import java.util.Optional;

public interface CategoryService {
    Collection<Category> findAll();

    Optional<Category> findById(int id);
}

package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Category;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository {
    Collection<Category> findAll();

    Optional<Category> findById(int id);

    Collection<Category> getCategoriesByIds(Set<Integer> ids);
}

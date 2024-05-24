package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Priority;
import ru.job4j.model.Task;
import ru.job4j.repository.utils.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query("from Priority ORDER BY id ASC", Priority.class);
    }

    @Override
    public Optional<Priority> findById(int id) {
        return crudRepository.optional(
                "from Priority where id = :fId", Priority.class,
                Map.of("fId", id)
        );
    }
}

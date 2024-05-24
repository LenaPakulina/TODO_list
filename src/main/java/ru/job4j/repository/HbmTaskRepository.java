package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;
import ru.job4j.repository.utils.CrudRepository;

import java.util.*;

@AllArgsConstructor
@Repository
public class HbmTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.runWithResult(
                "DELETE Task WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.runFunction(session -> session.merge(task) != null);
    }

    @Override
    public boolean switchStatusDone(int id, boolean done) {
        return crudRepository.runWithResult(
            "UPDATE Task SET done = :fDone WHERE id = :fId",
            Map.of(
                    "fId", id,
                    "fDone", done
            )
        );
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "from Task t JOIN FETCH t.priority JOIN FETCH t.categories where t.id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("SELECT DISTINCT t from Task t JOIN FETCH t.priority JOIN FETCH t.categories ORDER BY t.id ASC", Task.class);
    }

    @Override
    public Collection<Task> findDoneTasks() {
        return findTaskByStatus(true);
    }

    @Override
    public Collection<Task> findNewTasks() {
        return findTaskByStatus(false);
    }

    private Collection<Task> findTaskByStatus(boolean isDone) {
        return crudRepository.query("SELECT DISTINCT t from Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE t.done = :fDone ORDER BY t.id ASC", Task.class,
                Map.of("fDone", isDone)
        );
    }
}

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
        return crudRepository.runWithResult(
                "UPDATE Task SET description = :fDesk, done = :fDone, title = :fTitle WHERE id = :fId",
                Map.of(
                        "fId", task.getId(),
                        "fDesk", task.getDescription(),
                        "fDone", task.isDone(),
                        "fTitle", task.getTitle()
                )
        );
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
                "from Task where id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("from Task order by id asc", Task.class);
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
        return crudRepository.query("from Task WHERE done = :fDone ORDER BY id ASC", Task.class,
                Map.of("fDone", isDone)
        );
    }
}

package ru.job4j.service;

import ru.job4j.model.Task;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface TaskService {
    Task save(Task task, Set<Integer> categories);

    boolean deleteById(int id);

    boolean update(Task task, Set<Integer> categories);

    boolean switchStatusDone(int id, boolean done);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findDoneTasks();

    Collection<Task> findNewTasks();
}

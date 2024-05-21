package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Task;
import ru.job4j.repository.TaskRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTaskService implements TaskService {
    private final TaskRepository repository;

    public SimpleTaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return repository.update(task);
    }

    @Override
    public boolean switchStatusDone(int id, boolean done) {
        return repository.switchStatusDone(id, done);
    }

    @Override
    public Optional<Task> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Task> findDoneTasks() {
        return repository.findDoneTasks();
    }

    @Override
    public Collection<Task> findNewTasks() {
        return repository.findNewTasks();
    }
}

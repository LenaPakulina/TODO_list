package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Category;
import ru.job4j.model.Task;
import ru.job4j.repository.CategoryRepository;
import ru.job4j.repository.TaskRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository repository;
    private final CategoryRepository categoryRepository;

    private void updateCategories(Task task, Set<Integer> categories) {
        Set<Category> result = new HashSet<>();
        for (Integer elem : categories) {
            Optional<Category> category = categoryRepository.findById(elem);
            category.ifPresent(result::add);
        }
        task.setCategories(result);
    }

    @Override
    public Task save(Task task, Set<Integer> categories) {
        updateCategories(task, categories);
        return repository.save(task);
    }

    @Override
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean update(Task task, Set<Integer> categories) {
        updateCategories(task, categories);
        boolean answer = repository.update(task);
        Optional<Task> sdf = repository.findById(task.getId());
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

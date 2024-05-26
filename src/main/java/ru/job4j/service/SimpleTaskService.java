package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Category;
import ru.job4j.model.Task;
import ru.job4j.repository.CategoryRepository;
import ru.job4j.repository.TaskRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository repository;
    private final CategoryRepository categoryRepository;

    private void updateCategories(Task task, Set<Integer> categories) {
        Collection<Category> allCategories = categoryRepository.getCategoriesByIds(categories);
        Set<Category> result = Set.copyOf(allCategories);
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
        return repository.update(task);
    }

    @Override
    public boolean switchStatusDone(int id, boolean done) {
        return repository.switchStatusDone(id, done);
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            setFixTimeZone(task);
            optionalTask = Optional.of(task);
        }
        return optionalTask;
    }

    @Override
    public Collection<Task> findAll() {
        Collection<Task> sdf = repository.findAll();
        return setFixTimeZone(sdf);
    }

    @Override
    public Collection<Task> findDoneTasks() {
        return setFixTimeZone(repository.findDoneTasks());
    }

    @Override
    public Collection<Task> findNewTasks() {
        return setFixTimeZone(repository.findNewTasks());
    }

    public void setFixTimeZone(Task task) {
        String timezone = task.getUser().getTimezone();
        if (timezone == null || timezone.isEmpty()) {
            timezone = TimeZone.getDefault().getID();
        }
        ZonedDateTime localTime = task.getCreated()
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of(timezone));
        task.setCreated(localTime.toLocalDateTime());
    }

    public Collection<Task> setFixTimeZone(Collection<Task> tasks) {
        for (Task task: tasks) {
            setFixTimeZone(task);
        }
        return tasks;
    }
}

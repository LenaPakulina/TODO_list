package ru.job4j.repository;

import ru.job4j.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
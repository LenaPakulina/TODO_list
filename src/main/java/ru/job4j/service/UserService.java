package ru.job4j.service;

import ru.job4j.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}

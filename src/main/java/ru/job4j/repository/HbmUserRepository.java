package ru.job4j.repository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.repository.utils.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class HbmUserRepository implements UserRepository {
    private final CrudRepository repository;

    public HbmUserRepository(CrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> save(User user) {
        try {
            repository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            log.error("Failed to save user.");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return repository.optional(
                "from User where login = :fLogin AND password = :fPassword", User.class,
                Map.of(
                        "fLogin", email,
                        "fPassword", password
                )
        );
    }
}

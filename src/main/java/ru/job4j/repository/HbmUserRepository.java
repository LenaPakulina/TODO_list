package ru.job4j.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.repository.utils.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
public class HbmUserRepository implements UserRepository {
    private final CrudRepository repository;

    public HbmUserRepository(CrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> save(User user) {
        Optional<User> result = Optional.of(user);
        try {
            repository.run(session -> session.persist(user));
        } catch (Exception e) {
            result = Optional.empty();
        }
        return result;
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

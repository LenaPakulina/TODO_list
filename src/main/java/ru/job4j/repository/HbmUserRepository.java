package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;
import ru.job4j.model.User;

import java.util.Optional;

@Repository
public class HbmUserRepository implements UserRepository {
    private final SessionFactory sf;

    public HbmUserRepository(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public User save(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> answer = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            answer = session.createQuery(
                            "from User where login = :fLogin AND password = :fPassword", User.class)
                    .setParameter("fLogin", email)
                    .setParameter("fPassword", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return answer;
    }
}

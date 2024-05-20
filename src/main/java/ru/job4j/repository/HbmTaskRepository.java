package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class HbmTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    public HbmTaskRepository(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        boolean answer = false;
        try {
            session.beginTransaction();
            answer = session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return answer;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean answer = false;
        try {
            session.beginTransaction();
            answer = session.createQuery(
                            "UPDATE Task SET description = :fDesk, done = :fDone WHERE id = :fId")
                    .setParameter("fName", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return answer;
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> answer = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            answer = session.createQuery(
                            "from Task where id = :fId", Task.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return answer;
    }

    @Override
    public Collection<Task> findAll() {
        List<Task> answer = new LinkedList<>();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("from Task", Task.class);
            answer = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return answer;
    }
}

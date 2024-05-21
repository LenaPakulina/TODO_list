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
                            "UPDATE Task SET description = :fDesk, done = :fDone, title = :fTitle WHERE id = :fId")
                    .setParameter("fDesk", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fTitle", task.getTitle())
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
    public boolean switchStatusDone(int id, boolean done) {
        Session session = sf.openSession();
        boolean answer = false;
        try {
            session.beginTransaction();
            answer = session.createQuery(
                            "UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", done)
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
            Query<Task> query = session.createQuery("from Task ORDER BY id ASC", Task.class);
            answer = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return answer;
    }

    @Override
    public Collection<Task> findDoneTasks() {
        return findTaskByStatus(true);
    }

    @Override
    public Collection<Task> findNewTasks() {
        return findTaskByStatus(false);
    }

    private Collection<Task> findTaskByStatus(boolean isDone) {
        List<Task> answer = new LinkedList<>();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("from Task WHERE done = :fDone ORDER BY id ASC", Task.class)
                    .setParameter("fDone", isDone);
            answer = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return answer;
    }
}

package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;

import java.util.List;

public class HbmStore implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbmStore() { }

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Task add(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        }
        return task;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            tasks = session.createQuery("from Task order by id").list();
            session.getTransaction().commit();
        }
        return tasks;
    }

    @Override
    public List<Task> findNotCompleted() {
        List<Task> tasks;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            tasks = session.createQuery("from Task where done = false order by id").list();
            session.getTransaction().commit();
        }
        return tasks;
    }

    @Override
    public void saveTaskStatus(int id, boolean done) {
            try (Session session = sf.openSession()) {
                session.beginTransaction();
                Task task = session.get(Task.class, id);
                if (task != null) {
                    task.setDone(done);
                }
                session.getTransaction().commit();
        }
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
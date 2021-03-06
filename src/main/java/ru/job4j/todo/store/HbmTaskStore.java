package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.function.Function;

public class HbmTaskStore implements TaskStore, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbmTaskStore() {
    }

    private static final class Lazy {
        private static final TaskStore INST = new HbmTaskStore();
    }

    public static TaskStore instOf() {
        return Lazy.INST;
    }

    private <T> T execute(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Task add(Task task, List<Integer> cIdc) {
        return this.execute(
                session -> {
                    for (Integer id : cIdc) {
                        Category category = session.find(Category.class, id);
                        task.addCategory(category);
                    }
                    session.save(task);
                    return task;
                }
        );
    }

    @Override
    public List<Task> findAll() {
        return this.execute(
                session -> session.createQuery("select distinct t "
                        + "from Task t join fetch t.categories order by t.id").list());
    }

    @Override
    public List<Task> findNotCompleted() {
        return this.execute(
                session -> session.createQuery("select distinct t from Task t "
                        + "join fetch t.categories where t.done = false order by t.id").list());
    }

    @Override
    public boolean saveTaskStatus(int id, boolean done) {
        return this.execute(
                session -> {
                    boolean rsl = false;
                    Task task = session.get(Task.class, id);
                    if (task != null) {
                        task.setDone(done);
                        rsl = true;
                    }
                    return rsl;
                });
    }

    @Override
    public List<Category> findAllCategories() {
        return this.execute(session ->
                session.createQuery("from Category", Category.class).list());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
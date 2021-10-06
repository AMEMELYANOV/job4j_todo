package ru.job4j.books;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book  one = Book.of("Война и мир");
            Book two = Book.of("Анна Каренина");
            Book three = Book.of("Двенадцать стульев");

            Author first = Author.of("Л.Н. Толстой");
            Author second = Author.of("И. Ильф");
            Author third = Author.of("Е. Петров");

            first.getBooks().add(one);
            first.getBooks().add(two);
            second.getBooks().add(three);
            third.getBooks().add(three);

            session.persist(first);
            session.persist(second);

            session.persist(first);
            session.persist(second);
            session.persist(third);

            Author person = session.get(Author.class, 2);
            session.remove(person);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

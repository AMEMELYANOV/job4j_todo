package ru.job4j.car;

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

            Model one = Model.of("Corolla");
            Model two = Model.of("Land Cruiser Prado");
            Model three = Model.of("Land Cruiser 300");
            Model four = Model.of("Yaris");
            Model five = Model.of("Auris");

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            Brand brand = Brand.of("TOYOTA");
            brand.addModel(session.load(Model.class, 1));
            brand.addModel(session.load(Model.class, 2));
            brand.addModel(session.load(Model.class, 3));
            brand.addModel(session.load(Model.class, 4));
            brand.addModel(session.load(Model.class, 5));

            session.save(brand);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

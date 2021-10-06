package ru.job4j.auto;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HibernateRun {
    public static void main(String[] args) {
        List<Brand> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Brand brand = Brand.of("TOYOTA");

            Model one = Model.of("Corolla");
            session.save(one);
            Model two = Model.of("Land Cruiser Prado");
            session.save(two);
            Model three = Model.of("Land Cruiser 300");
            session.save(three);

            brand.addModel(session.load(Model.class, 1));
            brand.addModel(session.load(Model.class, 2));
            brand.addModel(session.load(Model.class, 3));

            session.save(brand);

            list = session.createQuery("select distinct b from Brand b join fetch b.models").list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Brand brand : list) {
            for (Model model : brand.getModels()) {
                System.out.println(model);
            }
        }
    }
}

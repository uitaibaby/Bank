package SQL;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import DAO.Event;

import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).addAnnotatedClass(Event.class).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(new Event(1990.00, new Date()));
        session.save(new Event(10000, new Date()));
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Event").list();
        for (Event event : (List<Event>) result) {
            System.out.println("Event (" + event.getDate() + ") : " + event.getBalance());
        }
        session.getTransaction().commit();
        session.close();

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
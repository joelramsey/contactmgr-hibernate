package com.ramsey.contactmgr;


import com.ramsey.contactmgr.model.Contact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.imageio.spi.ServiceRegistry;

public class Application {

    //Reusable ref to a session factory
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        //Creating a standard service registry object
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("Joel", "Ramsey")
                .withEmail("joel@gmail.com")
                .withPhone(5555555555L)
                .build();
        // Open session
        Session session = sessionFactory.openSession();

        //Begin transaction
        session.beginTransaction();

        //Use the session to save the transaction
        session.save(contact);

        //Commit the transaction
        session.getTransaction().commit();

        //Close the session
        session.close();
    }
}

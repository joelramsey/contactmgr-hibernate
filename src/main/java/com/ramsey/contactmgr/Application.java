package com.ramsey.contactmgr;


import com.ramsey.contactmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.imageio.spi.ServiceRegistry;
import java.util.List;

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

        int id = save(contact);

        // Display a list of contacts before update
        System.out.printf("%n%nBefore update%n%n");
        fetchAllContacts().stream().forEach((System.out::print));

        // Get persisted contact
        Contact c = findContactById(id);

        // Update the contact
        c.setFirstName("Myself");

        // Persist the changes
        System.out.printf("%n%nUpdating...%n%n");
        update(c);
        System.out.printf("%n%nUpdate complete!%n%n");

        // Display a list of contacts after the update
        System.out.printf("%n%nAfter%n%n");
        fetchAllContacts().stream().forEach((System.out::print));
    }

    private static  Contact findContactById(int id) {
        // Open session
        Session session = sessionFactory.openSession();

        //Create a criteria object
        Contact contact = session.get(Contact.class,id);

        //Close the session
        session.close();

        //Return the contact  object
        return contact;
    }

    private static void update(Contact contact) {
        // Open session
        Session session = sessionFactory.openSession();

        //Begin transaction
        session.beginTransaction();

        //Use the session to save the transaction
        session.update(contact);

        //Commit the transaction
        session.getTransaction().commit();

        //Close the session
        session.close();
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        // Open session
        Session session = sessionFactory.openSession();

        //Create a criteria object
        Criteria criteria = session.createCriteria(Contact.class);

        //Get a list of contact objects according to the Criteria object
        List<Contact> contacts = criteria.list();

        //Close the session
        session.close();

        return contacts;
    }

    private static int save(Contact contact) {
        // Open session
        Session session = sessionFactory.openSession();

        //Begin transaction
        session.beginTransaction();

        //Use the session to save the transaction
        int id = (int)session.save(contact);

        //Commit the transaction
        session.getTransaction().commit();

        //Close the session
        session.close();

        return id;
    }
}

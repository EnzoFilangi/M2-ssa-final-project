package com.ssa.team3.backend.model.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@ApplicationScoped
public class HibernateUtil {
    // From : https://docs.jboss.org/hibernate/orm/6.0/quickstart/html_single/
    // Annotation based configuration
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionAnnotationFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings and classes with annotations from hibernate.cfg.xml
                .build();

        return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    private static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionAnnotationFactory();
        return sessionFactory;
    }

    public static Session beginTransaction(){
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return session;
    }

    public static void endTransaction(Session session){
        session.getTransaction().commit();
        session.close();
    }
}

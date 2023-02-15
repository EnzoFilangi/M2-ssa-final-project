package com.ssa.team3.backend.model.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

// From : https://docs.jboss.org/hibernate/orm/6.0/quickstart/html_single/
public class HibernateUtil {
    //Annotation based configuration
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionAnnotationFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings and classes with annotations from hibernate.cfg.xml
                .build();

        return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionAnnotationFactory();
        return sessionFactory;
    }
}

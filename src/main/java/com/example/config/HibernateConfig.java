package com.example.config;

import com.example.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(People.class);
            configuration.addAnnotatedClass(Dialogs.class);
//      configuration.addAnnotatedClass(Building.class);
//      configuration.addAnnotatedClass(Event.class);
            configuration.addAnnotatedClass(Friends.class);
            configuration.addAnnotatedClass(School.class);
            configuration.addAnnotatedClass(UserPosts.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
}

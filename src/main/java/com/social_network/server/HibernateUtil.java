package com.social_network.server;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration();
        config.configure();
        return config.buildSessionFactory();
    }
}

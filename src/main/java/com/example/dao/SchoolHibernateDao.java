package com.example.dao;

import com.example.config.HibernateConfig;
import com.example.entities.School;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SchoolHibernateDao {
    public void save(School school) {

        Transaction transaction = null;

        // auto close session object
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {

            // start the transaction
            transaction = session.beginTransaction();

            // save a object
            session.save(school);

            // commit transction
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}

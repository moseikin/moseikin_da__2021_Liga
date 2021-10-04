package com.example.dao;

import com.example.config.JpaConfig;
import com.example.entities.People;

import javax.persistence.EntityManager;

public class PeopleJpaDao {

    public People save(People people) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();

        try{
            entityManager.getTransaction().begin();
            if (people.getId() == null) {
                entityManager.persist(people);
            } else{
                people = entityManager.merge(people);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
        return people;
    }
}

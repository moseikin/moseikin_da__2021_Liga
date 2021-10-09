package com.example.dao;

import com.example.config.JpaConfig;
import com.example.entities.School;

import javax.persistence.EntityManager;

public class SchoolJpaDao {

    public School save(School school){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            if (school.getNumber() == null) {
                entityManager.persist(school);
            } else{
                school = entityManager.merge(school);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
       return school;
    }

    public School findSchoolById(Long id) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        return entityManager.find(School.class, id);
    }

}

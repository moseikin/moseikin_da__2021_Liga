package com.example.dao;

import com.example.config.JpaConfig;
import com.example.entities.School;
import com.example.entities.Usr;

import javax.persistence.EntityManager;
import java.util.List;

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



//    public List<Usr> getAllUsrInSchool(School school) {
//        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
//        return entityManager.createQuery(
//                "SELECT a FROM Usr WHERE a.getSchool.getNumber:=school.getNumber", Usr.class);
//    }

//    public List<Usr> getAllUsrInSchool(School school) {
//        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
//        return entityManager.createQuery(
//                "SELECT a FROM Usr WHERE a.getSchool.getNumber:=school.getNumber", Usr.class);
//    }




}

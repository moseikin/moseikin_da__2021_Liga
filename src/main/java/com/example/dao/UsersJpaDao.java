package com.example.dao;

import com.example.config.JpaConfig;
import com.example.entities.Usr;

import javax.persistence.EntityManager;
import java.util.List;

public class UsersJpaDao {

    public Usr save(Usr usr) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            if (usr.getId() == null) {
                entityManager.persist(usr);
            } else{
                usr = entityManager.merge(usr);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
        return usr;
    }

    public Usr findById(Long id){
        return JpaConfig.getEntityManagerFactory().createEntityManager().find(Usr.class, id);
    }

    public List<Usr> usrList(){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        return entityManager.createQuery("SELECT a from Usr a", Usr.class).getResultList();
    }

    public void deleteUsr(Usr usr) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(usr) ? usr : entityManager.merge(usr));
        entityManager.getTransaction().commit();
    }
}

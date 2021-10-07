package com.example.dao;

import com.example.config.JpaConfig;
import com.example.entities.UserPosts;

import javax.persistence.EntityManager;

public class UsersPostsJpaDao {

    public void addPost(UserPosts userPosts){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();

        try {
            entityManager.getTransaction().begin();
            if (userPosts.getId() == null) {
                entityManager.persist(userPosts);
            } else {
                entityManager.merge(userPosts);
            }
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }

    }
}

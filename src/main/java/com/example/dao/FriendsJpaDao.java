package com.example.dao;

import com.example.config.JpaConfig;
import com.example.entities.Friends;
import com.example.entities.Usr;
import org.apache.catalina.LifecycleState;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class FriendsJpaDao {

    public void addNewRequest(Friends friends){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        try {
            if (getRequest(friends) == null) {
                entityManager.getTransaction().begin();
                entityManager.persist(friends);
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            entityManager.close();
            throw e;
        }
    }

    public Friends getRequest(Friends friends) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        List<Friends> friendsList = entityManager.createQuery(
                "select f from Friends f where f.friendsPk.ownerId=:owner_id and f.friendsPk.usrId=:usr_id")
                .setParameter("owner_id", friends.getFriendsPk().getOwnerId())
                .setParameter("usr_id", friends.getFriendsPk().getUsrId()).getResultList();
        if (friendsList.isEmpty()) {
            return null;
        } else {
            return friendsList.get(0);
        }

    }

    public List<Friends> getAllIncomingRequests(Usr usr){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        return entityManager.createQuery(
                "select f from Friends f where f.friendsPk.usrId=:id and f.areFriends=false")
                            .setParameter("id", usr.getId())
                            .getResultList();
    }

    public void changeAreFriendsCondition(Friends friends){
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(friends);
        entityManager.getTransaction().commit();
    }

    public void rejectRequest(Friends friends) {
        EntityManager em = JpaConfig.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        em.remove((em.contains(friends) ? friends : em.merge(friends)));
        em.getTransaction().commit();
    }

    public List<Friends> getAllOutgoingRequest(Usr usr) {
        EntityManager entityManager = JpaConfig.getEntityManagerFactory().createEntityManager();
        return entityManager.createQuery(
                "select f from Friends f where f.friendsPk.ownerId=:id and f.areFriends=false")
                .setParameter("id", usr.getId())
                .getResultList();
    }
}

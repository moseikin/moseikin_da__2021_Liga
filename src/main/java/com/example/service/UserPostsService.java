package com.example.service;

import com.example.dao.UsersJpaDao;
import com.example.dao.UsersPostsJpaDao;
import com.example.entities.UserPosts;
import com.example.entities.Usr;

public class UserPostsService {
    private static final UsersJpaDao USERS_JPA_DAO = new UsersJpaDao();
    private static final UsersPostsJpaDao USERS_POSTS_JPA_DAO = new UsersPostsJpaDao();

        public static void addPost(){
            Usr usr = USERS_JPA_DAO.findById(1L);
            // включить проверку на usr != null
            UserPosts userPosts = new UserPosts();
            String s = "Первый пост первого юзера";
            userPosts.setUsr(usr).setText(s);
            USERS_POSTS_JPA_DAO.addPost(userPosts);
        }
}

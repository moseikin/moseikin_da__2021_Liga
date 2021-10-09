package com.example.service;

import com.example.dao.UsersJpaDao;
import com.example.dao.UsersPostsJpaDao;
import com.example.entities.UserPosts;
import com.example.entities.Usr;

import java.util.List;

public class UserPostsService {
    private static final UsersJpaDao USERS_JPA_DAO = new UsersJpaDao();
    private static final UsersPostsJpaDao USERS_POSTS_JPA_DAO = new UsersPostsJpaDao();

        public static void addPost(){
            List<Usr> usrList = USERS_JPA_DAO.usrList();
            Usr usr = usrList.get(0);

            UserPosts userPosts = new UserPosts();
            String s = "Первый пост первого юзера";
            userPosts.setUsr(usr).setText(s);
            USERS_POSTS_JPA_DAO.addPost(userPosts);

        }
}

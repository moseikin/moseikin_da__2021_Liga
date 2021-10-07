package com.example;

import com.example.dao.SchoolJpaDao;
import com.example.dao.UsersJpaDao;
import com.example.dao.UsersPostsJpaDao;
import com.example.entities.School;
import com.example.entities.UserPosts;
import com.example.entities.Usr;
import com.example.service.SchoolService;
import com.example.service.UserService;

public class AddContent {
    private static final UsersJpaDao usersJpaDao = new UsersJpaDao();
    private static final UsersPostsJpaDao usersPostsJpaDao = new UsersPostsJpaDao();
    public static final SchoolJpaDao SCHOOL_JPA_DAO = new SchoolJpaDao();

    public static void addUser(){
        UserService.addUsers();
    }



    public static void allUsrInSchool(){
        School school = SCHOOL_JPA_DAO.findSchoolById(1L);
        for (Usr usr: school.getUsrList()) {
            System.out.println("Usr: " + usr);
        }

        School school2 = SCHOOL_JPA_DAO.findSchoolById(2L);
        for (Usr usr: school2.getUsrList()) {
            System.out.println("Usr: " + usr);
        }
    }

}

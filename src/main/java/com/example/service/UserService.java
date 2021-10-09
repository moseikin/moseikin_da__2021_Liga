package com.example.service;

import com.example.dao.SchoolJpaDao;
import com.example.dao.UsersJpaDao;
import com.example.entities.School;

import com.example.entities.Usr;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public class UserService {
    private static final UsersJpaDao usersJpaDao = new UsersJpaDao();
    private static final SchoolJpaDao schoolJpaDao = new SchoolJpaDao();

    public static void addUsers(){
        School school = schoolJpaDao.findSchoolById(1L);
        List<Usr> usrList = usrList();
        usrList.get(0).setSchool(school);
        usrList.forEach(usersJpaDao::save);
    }

    private static List<Usr> usrList(){
        return List.of(new Usr("john", "smith", 45),
                new Usr("sam", "lastname", 11),
                new Usr("adam", "dru", 8));
    }

    public static void getAllUsers(){
        for (Usr usr: usersJpaDao.usrList()){
            System.out.println(usr);
        }
    }

    public static void deleteUser(){
        Usr usr = usersJpaDao.usrList().get(0);
        usersJpaDao.deleteUsr(usr);
    }

    public static void deleteAllUsers(){
        usersJpaDao.usrList().forEach(usersJpaDao::deleteUsr);
    }

}

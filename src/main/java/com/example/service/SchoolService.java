package com.example.service;

import com.example.dao.SchoolJpaDao;
import com.example.entities.School;
import com.example.entities.Usr;

import java.util.List;

public class SchoolService {

    private static final SchoolJpaDao SCHOOL_JPA_DAO = new SchoolJpaDao();

    public static void addSchool(){
        School schools = new School(1L, "Разъезжая, 2");
        School schools2 = new School(2L, "Конюшенная, 18");

        SCHOOL_JPA_DAO.save(schools);
        SCHOOL_JPA_DAO.save(schools2);
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

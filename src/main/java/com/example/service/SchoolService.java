package com.example.service;

import com.example.dao.SchoolJpaDao;
import com.example.entities.School;
import com.example.entities.Usr;

import java.util.List;

public class SchoolService {

    private static final SchoolJpaDao schoolJpaDao = new SchoolJpaDao();

    public static void addSchool(){
        School schools = new School(1L, "Разъезжая, 2");
        School schools2 = new School(2L, "Конюшенная, 18");

        schoolJpaDao.save(schools);
        schoolJpaDao.save(schools2);
    }

    public static List<Usr> getAllUsrInSchool(School school){
        return school.getUsrList();
    }


}

package com.example.repository.service;

import com.example.dao.PeopleJpaDao;
import com.example.dao.SchoolHibernateDao;
import com.example.dao.SchoolJpaDao;
import com.example.entities.Address;
import com.example.entities.People;
import com.example.entities.School;

import java.util.List;

public class BasicService {

    public static void addPeople(){
        PeopleJpaDao peopleJpaDao = new PeopleJpaDao();
        List<People> peopleList = createPeople();
        for (int i = 0; i < peopleList.size(); i ++){
            System.out.println("saving =======" + peopleList.get(i));
            peopleJpaDao.save(peopleList.get(i));
        }
//        peopleList.forEach(peopleJpaDao::save);
    }

    public static List<People> createPeople(){
        return List.of(new People("John", "Smith", 1),
                new People("Andrew", "Surname", 1),
                new People("Joseph", "Mihalow", 2));
    }

    public static void addSchool(){
        SchoolJpaDao schoolJpaDao = new SchoolJpaDao();
//        SchoolHibernateDao schoolHibernateDao = new SchoolHibernateDao();
        List<School> schools = schools();
        schools.forEach(schoolJpaDao::save);
//        schools.forEach(schoolHibernateDao::save);

    }

    public static List<School> schools(){
        return List.of(
                new School(
//                        1L,
                    new Address("RU", "SPb", "Nevskiy", 13)),
                new School(
//                        2L,
                    new Address("RU", "SPb", "Ligovsky", 100)));
    }
}

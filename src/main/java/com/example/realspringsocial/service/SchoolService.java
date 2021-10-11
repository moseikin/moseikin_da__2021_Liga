package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.repo.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    public School findSchoolByNumber(Long schoolNumber, String address){
        System.out.println("SCHOL REPO = " + schoolRepository);
        Optional<School> optional = schoolRepository.findById(schoolNumber);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            return addSchool(schoolNumber, address);
        }
    }

    public School addSchool(Long schoolNumber, String address) {
        School school = new School(schoolNumber, address);
        schoolRepository.save(school);
        return school;
    }
}

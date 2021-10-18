package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.repo.SchoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public Iterable<School> allSchools(){
        return schoolRepository.findAll();
    }

    public School findSchoolByNumber(Long schoolNumber, String address){
        Optional<School> optional = schoolRepository.findById(schoolNumber);
        return optional.orElseGet(() -> addSchool(schoolNumber, address));
    }

    @Transactional()
    public School addSchool(Long schoolNumber, String address) {
        School school = new School(schoolNumber, address);
        schoolRepository.save(school);
        return school;
    }
}

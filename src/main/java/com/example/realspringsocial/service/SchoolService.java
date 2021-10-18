package com.example.realspringsocial.service;

import com.example.realspringsocial.entity.School;
import com.example.realspringsocial.repo.SchoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    @Transactional
    public String deleteSchool(Long schoolNumber) {
        Optional<School> optional = schoolRepository.findById(schoolNumber);
        if (optional.isPresent()) {
            schoolRepository.delete(optional.get());
            return "deleted";
        } else {
            throw new EntityNotFoundException();
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public School addSchool(Long schoolNumber, String address) {
        School school = new School(schoolNumber, address);
        schoolRepository.save(school);
        return school;
    }
}

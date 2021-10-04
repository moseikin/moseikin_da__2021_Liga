package com.example.repository;

import com.example.entities.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {

    List<People> findAllById(Long id);

    @Override
    List<People> findAll();

    List<People> findByNameIgnoreCase(String peopleName);

    List<People> findBySurnameIgnoreCase(String peopleSurName);

    List<People> findBySchoolNumber(Integer schoolNumber);

    People deletePeopleById(Long id);




}

package com.example.repository;

import com.example.entities.School;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Override
    List<School> findAll(Sort sort);

    List<School>findBySchoolNumber(Long schoolNumber);
}

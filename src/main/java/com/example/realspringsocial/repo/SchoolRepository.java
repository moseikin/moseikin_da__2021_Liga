package com.example.realspringsocial.repo;

import com.example.realspringsocial.entity.School;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends CrudRepository<School, Long> {}

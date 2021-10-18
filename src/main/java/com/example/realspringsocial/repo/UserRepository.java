package com.example.realspringsocial.repo;

import com.example.realspringsocial.entity.Usr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Usr, Long> {

}

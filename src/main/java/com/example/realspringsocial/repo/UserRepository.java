package com.example.realspringsocial.repo;

import com.example.realspringsocial.entity.Usr;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Usr, Long> {

}

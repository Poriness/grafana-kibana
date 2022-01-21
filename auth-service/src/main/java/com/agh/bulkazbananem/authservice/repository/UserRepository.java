package com.agh.bulkazbananem.authservice.repository;

import com.agh.bulkazbananem.authservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    List<User> findAll();

}
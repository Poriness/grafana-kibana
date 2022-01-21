package com.agh.bulkazbananem.usersservice.repository;

import com.agh.bulkazbananem.usersservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByLastname(String lastname);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    List<User> findAll();

}
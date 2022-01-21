package com.agh.bulkazbananem.usersservice.repository;

import com.agh.bulkazbananem.usersservice.model.Dietitian;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DietitianRepository extends CrudRepository<Dietitian, Integer>{
    Optional<Dietitian> findByUsername(String username);

    List<Dietitian> findAll();

}


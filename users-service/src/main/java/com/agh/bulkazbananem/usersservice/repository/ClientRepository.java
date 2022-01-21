package com.agh.bulkazbananem.usersservice.repository;

import com.agh.bulkazbananem.usersservice.model.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    Optional<Client> findByUsername(String username);
    Boolean existsByUsername(String username);
    List<Client> findAllByDietitianUsername(String username);
    List<Client> findAll();

}
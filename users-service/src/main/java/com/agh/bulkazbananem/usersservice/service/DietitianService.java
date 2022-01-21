package com.agh.bulkazbananem.usersservice.service;

import com.agh.bulkazbananem.usersservice.model.Client;
import com.agh.bulkazbananem.usersservice.model.Dietitian;
import com.agh.bulkazbananem.usersservice.repository.ClientRepository;
import com.agh.bulkazbananem.usersservice.repository.DietitianRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietitianService {

    @Autowired
    private DietitianRepository dietitianRepository;
    @Autowired
    private ClientRepository clientRepository;

    public List<Dietitian> getAllDietitians() {
        return dietitianRepository.findAll();
    }

    public List<Client> findAllClientsByUsername(String dietitianUsername) {
        return clientRepository.findAllByDietitianUsername(dietitianUsername);
    }
}


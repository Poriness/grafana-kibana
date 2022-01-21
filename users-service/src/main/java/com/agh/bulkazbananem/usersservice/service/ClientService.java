package com.agh.bulkazbananem.usersservice.service;

import com.agh.bulkazbananem.usersservice.model.Client;
import com.agh.bulkazbananem.usersservice.model.Dietitian;
import com.agh.bulkazbananem.usersservice.repository.ClientRepository;
import com.agh.bulkazbananem.usersservice.repository.DietitianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DietitianRepository dietitianRepository;

    public Optional<Client> getClientByUsername(String username){
        return clientRepository.findByUsername(username);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    public void associateDietitian(String clientUsername, String dietitianUsername) throws Exception {
        Client client = clientRepository.findByUsername(clientUsername).
                orElseThrow(() -> new Exception("The client '" + clientUsername + "' was not found"));
        Dietitian dietitian = dietitianRepository.findByUsername(dietitianUsername)
                .orElseThrow(() -> new Exception("The dietitian '" + dietitianUsername + "' was not found"));
        client.setDietitian(dietitian);
        clientRepository.save(client);
    }
}

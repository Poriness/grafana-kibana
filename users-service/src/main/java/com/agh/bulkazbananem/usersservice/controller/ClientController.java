package com.agh.bulkazbananem.usersservice.controller;

import com.agh.bulkazbananem.usersservice.dto.AssociationRequest;
import com.agh.bulkazbananem.usersservice.dto.ClientResponse;
import com.agh.bulkazbananem.usersservice.dto.DietitianResponse;
import com.agh.bulkazbananem.usersservice.dto.MessageResponse;
import com.agh.bulkazbananem.usersservice.model.Client;
import com.agh.bulkazbananem.usersservice.model.Dietitian;
import com.agh.bulkazbananem.usersservice.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client/all")
    public List<ClientResponse> getAllClients() {
        log.info("INFO - get /user/all");
        return clientService.getAllClients().stream()
                .map(ClientResponse::new)
                .collect(Collectors.toList());
    }


    @PostMapping("/client/associate")
    public ResponseEntity<?> associateDietitian(@RequestBody AssociationRequest associationRequest) {
        try {
            clientService.associateDietitian(associationRequest.getClientUsername(), associationRequest.getDietitianUsername());
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
        String m = "Successfully associate dietitian '" + associationRequest.getDietitianUsername() + "' to client '" + associationRequest.getClientUsername() + "'.";
        log.info(m);
        return ResponseEntity.ok(new MessageResponse(m));
    }


    @GetMapping("/client/dietitian/{clientUsername}")
    public ResponseEntity<?> getDietitian(@PathVariable String clientUsername) {
        if (clientService.existsByUsername(clientUsername)) {
            Dietitian dietitian = clientService.getClientByUsername(clientUsername)
                    .map(Client::getDietitian)
                    .orElse(null);
            return dietitian == null
                    ? ResponseEntity.ok(new MessageResponse("The dietitian is not associated to the client"))
                    : ResponseEntity.ok(new DietitianResponse(dietitian));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("The client does not exist."));
    }

}

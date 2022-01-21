package com.agh.bulkazbananem.usersservice.controller;

import com.agh.bulkazbananem.usersservice.dto.ClientResponse;
import com.agh.bulkazbananem.usersservice.dto.DietitianResponse;
import com.agh.bulkazbananem.usersservice.dto.MessageResponse;
import com.agh.bulkazbananem.usersservice.dto.UserDto;
import com.agh.bulkazbananem.usersservice.service.DietitianService;
import com.agh.bulkazbananem.usersservice.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DietitianController {

    private final DietitianService dietitianService;
    private final UserService userService;

    public DietitianController(DietitianService dietitianService, UserService userService) {
        this.dietitianService = dietitianService;
        this.userService = userService;
    }

    @GetMapping("/dietitian/all")
    public List<DietitianResponse> getAllDietitians() {
        return dietitianService.getAllDietitians().stream()
                .map(DietitianResponse::new)
                .collect(Collectors.toList());
    }

    //do tworzenia przez admina
    @PostMapping("/dietitian/create")
    public ResponseEntity<?> addDietitian(@RequestBody UserDto newUser) {
        try {
            userService.registerDietitian(newUser);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
        log.info("[DIETITIAN CREATED]");
        return ResponseEntity.ok(new MessageResponse("Dietitian registered successfully!"));
    }

    @GetMapping("/dietitian/clients/{dietitianUsername}")
    public List<ClientResponse> getDietitianClients(@PathVariable String dietitianUsername) {
        return dietitianService.findAllClientsByUsername(dietitianUsername).stream()
                .map(ClientResponse::new)
                .collect(Collectors.toList());
    }

}

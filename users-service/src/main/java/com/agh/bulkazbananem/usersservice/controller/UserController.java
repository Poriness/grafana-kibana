package com.agh.bulkazbananem.usersservice.controller;

import com.agh.bulkazbananem.usersservice.dto.MessageResponse;
import com.agh.bulkazbananem.usersservice.dto.UserDto;
import com.agh.bulkazbananem.usersservice.dto.UserResponse;
import com.agh.bulkazbananem.usersservice.model.RoleOfUser;
import com.agh.bulkazbananem.usersservice.model.StateOfUser;
import com.agh.bulkazbananem.usersservice.model.User;
import com.agh.bulkazbananem.usersservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/all")
    public List<UserResponse> getAllUsers() {
        log.info("INFO - get /user/all");
        return userService.getUsers().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto newUser) {
        try {
            RoleOfUser role = newUser.getRole();
            if (role == null) {
                RoleOfUser userRole = RoleOfUser.CLIENT;
                newUser.setRole(userRole);
            }
            userService.register(newUser);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }

        log.info("Registered new user: " + newUser.getUsername());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> createClient(@Valid @RequestBody UserDto newUser) {
        try {
            userService.registerClient(newUser, StateOfUser.ACTIVE);
        } catch (Exception e) {
            log.error("[CANNOT CREATE USER]");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }

        log.info("Created new user: " + newUser.getUsername());
        return ResponseEntity.ok(new MessageResponse("User registered and activated successfully!"));
    }

    @PutMapping("/user/activate/{username}")
    public ResponseEntity<?> activateClient(@PathVariable String username) {
        Optional<User> userOptional = userService.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("Error: User with username %s does not exists!", username)));
        }
        if (userOptional.get().getState() == StateOfUser.ACTIVE) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("User %s is already active!", username)));
        }
        userService.activateClient(username);
        log.info("Activated user:  " + username);
        return ResponseEntity.ok(new MessageResponse("User activated successfully!"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        Optional<User> user = userService.findUserById(userId);
        if (user.isEmpty()) {
            log.info("This user does not exist:  " + userId);
            return ResponseEntity.ok(new MessageResponse(String.format("There is no user with id %s", userId)));
        }
        return ResponseEntity.ok(new UserResponse(user.get()));
    }

    @GetMapping("/user/assigned/client/{clientId}/dietitian/{dietitianId}")
    boolean isDietitianConnectedWithClient(@PathVariable int clientId, @PathVariable int dietitianId) {
        return userService.isClientAssignToDietitian(clientId, dietitianId);
    }

}
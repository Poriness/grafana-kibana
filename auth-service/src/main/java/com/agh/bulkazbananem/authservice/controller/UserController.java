package com.agh.bulkazbananem.authservice.controller;

import com.agh.bulkazbananem.authservice.dto.*;
import com.agh.bulkazbananem.authservice.model.UserDetailsImpl;
import com.agh.bulkazbananem.authservice.service.UserDetailsServiceImpl;
import com.agh.bulkazbananem.authservice.util.JwtUtil;
import com.agh.bulkazbananem.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/auth/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception(e);
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtTokenUtil.generateToken(userDetails);
        log.info("[Token generation]");
        return ResponseEntity.ok(new AuthenticationResponse(jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getRole()));
    }

    @PostMapping("/auth/validate")
    public String validateToken(@RequestBody ValidationRequest validationRequest) {
        String token = validationRequest.getToken();
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username;
        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            return "Token is not valid! " + e.getMessage();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        try {
            if (jwtTokenUtil.validateToken(token, userDetails)) {
                log.info("[Successful token validation]");
                return "Token is valid";
            }
            log.info("[Token is not valid!]");
            return "Token is not valid!";
        } catch (Exception e) {
            log.info("[Token is not valid!]");
            return "Token is not valid! " + e.getMessage();
        }
    }

}
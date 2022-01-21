package com.agh.bulkazbananem.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class AuthenticationResponse {

    private String token;
    private String type;
    private Integer id;
    private String username;
    private String role;

}

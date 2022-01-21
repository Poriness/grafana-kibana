package com.agh.bulkazbananem.usersservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="TDietitians")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Dietitian extends User{

    public Dietitian(String firstname, String lastname, String username, String password, RoleOfUser role, StateOfUser state) {
        super(firstname, lastname, username, password, role, state);
        this.username = username;
    }

    @Column(name="username")
    private String username;
}

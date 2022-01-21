package com.agh.bulkazbananem.usersservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="TClients")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Client extends User {

    @ManyToOne
    private Dietitian dietitian;

    public Client(String firstname, String lastname, String username, String encodePassword, RoleOfUser role, StateOfUser state) {
        super(firstname, lastname, username, encodePassword, role, state);
    }

}

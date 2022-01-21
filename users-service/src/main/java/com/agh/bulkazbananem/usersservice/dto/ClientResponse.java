package com.agh.bulkazbananem.usersservice.dto;

import com.agh.bulkazbananem.usersservice.model.Client;
import com.agh.bulkazbananem.usersservice.model.Dietitian;
import com.agh.bulkazbananem.usersservice.model.RoleOfUser;
import com.agh.bulkazbananem.usersservice.model.StateOfUser;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private String firstname;
    private String lastname;
    private String username;
    private StateOfUser state;
    private Integer userId;
    private Integer clientId;

    public ClientResponse(Client client) {
        this.firstname = client.getFirstname();
        this.lastname = client.getLastname();
        this.username = client.getUsername();
        this.state = client.getState();
        this.userId = client.getId();
        this.clientId = client.getId();
    }


}

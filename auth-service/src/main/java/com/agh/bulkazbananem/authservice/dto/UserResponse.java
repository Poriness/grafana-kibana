package com.agh.bulkazbananem.authservice.dto;

import com.agh.bulkazbananem.authservice.model.RoleOfUser;
import com.agh.bulkazbananem.authservice.model.StateOfUser;
import com.agh.bulkazbananem.authservice.model.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String firstname;
    private String lastname;
    private String username;
    private StateOfUser state;
    private RoleOfUser role;

    public UserResponse(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.username = user.getUsername();
        this.state = user.getState();
        this.role = user.getRole();
    }


}

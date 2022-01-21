package com.agh.bulkazbananem.usersservice.dto;

import com.agh.bulkazbananem.usersservice.model.RoleOfUser;
import com.agh.bulkazbananem.usersservice.model.StateOfUser;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private StateOfUser state;
    @NotNull
    private RoleOfUser role;

    public UserDto(String firstname, String lastname, String username, String password, RoleOfUser role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

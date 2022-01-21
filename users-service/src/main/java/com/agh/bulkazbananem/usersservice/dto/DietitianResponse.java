package com.agh.bulkazbananem.usersservice.dto;

import com.agh.bulkazbananem.usersservice.model.Dietitian;
import com.agh.bulkazbananem.usersservice.model.RoleOfUser;
import com.agh.bulkazbananem.usersservice.model.StateOfUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DietitianResponse {

    private String firstname;
    private String lastname;
    private String username;
    private StateOfUser state;
    private RoleOfUser role;
    private Integer userId;
    private Integer dietitianId;

    public DietitianResponse(Dietitian dietitian) {
        this.firstname = dietitian.getFirstname();
        this.lastname = dietitian.getLastname();
        this.username = dietitian.getUsername();
        this.state = dietitian.getState();
        this.role = dietitian.getRole();
        this.userId = dietitian.getId();
        this.dietitianId = dietitian.getId();
    }

}

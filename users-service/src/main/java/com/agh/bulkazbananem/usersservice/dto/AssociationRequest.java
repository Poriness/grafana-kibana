package com.agh.bulkazbananem.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AssociationRequest {
    private String dietitianUsername;
    private String clientUsername;
}

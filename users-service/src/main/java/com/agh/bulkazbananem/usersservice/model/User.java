package com.agh.bulkazbananem.usersservice.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Table(name = "TUsers")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    public User(String firstname, String lastname, String username, String password, RoleOfUser role, StateOfUser state) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.state = state;
        this.createdAt = LocalDate.now();
    }

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @NotNull
    @Column
    private String username;

    @Column
    private String password;

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private StateOfUser state;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleOfUser role;

    @Column(name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    public Boolean isActive() {
        return this.state.compareTo(StateOfUser.ACTIVE) == 0;
    }
}






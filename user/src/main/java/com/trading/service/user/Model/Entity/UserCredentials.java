package com.trading.service.user.model.entity;

import lombok.*;


import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="UserCredentials")

public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;

    @Column(name="userName")
    String userName;

    @Column(name="password")
    String password;

    @Column(name="userID")
    int userID;

    @Column(name="role")
    String role;

    public UserCredentials(String userName, String password, int userID, String role) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
        this.role = role;
    }
}


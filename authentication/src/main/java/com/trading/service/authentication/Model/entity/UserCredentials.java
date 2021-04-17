package com.trading.service.authentication.Model.entity;

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
    @Column(name="userID")
    int userID;

    @Column(name="userName")
    String userName;
    @NonNull
    @Column(name="password")
    String password;
    @Column(name="role")
    String role;


}

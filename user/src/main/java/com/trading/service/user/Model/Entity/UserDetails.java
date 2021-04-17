package com.trading.service.user.model.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name="UserDetails")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    int id;



    @Column(name="USER_NAME")
    String userName;

    @NonNull
    @Column(name="FIRST_NAME")
    String firstName;

    @NonNull
    @Column(name="LAST_NAME")
    String lastName;

    @NonNull

    @Column(name="EMAIL")
    String email;

    @NonNull
    @Column(name="PHONE_NUMBER")
    String phoneNumber;

    @NonNull
    @Column(name="SSN")
    String ssn;

    public UserDetails(@NonNull String userName, @NonNull String firstName, @NonNull String lastName,
                       @NonNull  String email, @NonNull String phoneNumber, @NonNull String ssn) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
    }
}

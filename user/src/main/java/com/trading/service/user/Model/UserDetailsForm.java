package com.trading.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;



@Data
@AllArgsConstructor
public class UserDetailsForm {

    @NonNull
    String userName;

    @NonNull
    String firstName;

    @NonNull
    String lastName;

    @NonNull

    String email;

    @NonNull
    String phoneNumber;

    @NonNull
    String ssn;

    @NonNull
    String password;

}

package com.trading.service.user.model;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {
    @NonNull
    String firstName;

    @NonNull
    String lastName;

    @NonNull

    String email;

    @NonNull
    String phoneNumber;
}

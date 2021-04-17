package com.trading.service.authentication.Repository;


import com.trading.service.authentication.Model.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepo extends JpaRepository<UserCredentials,Integer> {
    UserCredentials getByUserName(String userName);
}

package com.trading.service.user.repository;

import com.trading.service.user.model.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials,Integer> {

    public UserCredentials findByUserName(String userName);
}

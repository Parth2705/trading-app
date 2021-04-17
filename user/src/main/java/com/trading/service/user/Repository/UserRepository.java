package com.trading.service.user.repository;

import com.trading.service.user.model.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
    public Optional<UserDetails> findUserByUserName(String userName);
}

package com.trading.service.stock.transaction.repository;

import com.trading.service.stock.transaction.model.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity,Integer> {
    List<UserActivity> findAllByUserID(int userId);
}

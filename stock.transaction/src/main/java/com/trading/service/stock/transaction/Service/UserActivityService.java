package com.trading.service.stock.transaction.service;

import com.trading.service.stock.transaction.model.Activity;
import com.trading.service.stock.transaction.model.entity.UserActivity;
import com.trading.service.stock.transaction.model.Stock;
import com.trading.service.stock.transaction.repository.UserActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class UserActivityService {
    @Autowired
    UserActivityRepository userActivityRepository;

    public void addActivity(Activity activity, int userId, Stock stock) {
        log.info("Stock Transaction service: Inside addActivity- UserActivityService layer");
        UserActivity userActivity=new UserActivity(
                userId,
                stock.getSymbol(),
                activity.name(),
                stock.getQuantity(),
                stock.getPricePerStock(),
                stock.getPricePerStock()*stock.getQuantity(),
                new Date()
        );
        userActivityRepository.save(userActivity);
    }

    public List<UserActivity> getUserActivity(int userId) {
        log.info("Stock Transaction service: Inside getUserActivity- UserActivityService layer");
        return userActivityRepository.findAllByUserID(userId);
    }

    public List<UserActivity> getAllUserActivity() {
        log.info("Stock Transaction service: Inside getAllUserActivity- UserActivityService layer");
        return userActivityRepository.findAll();
    }
}

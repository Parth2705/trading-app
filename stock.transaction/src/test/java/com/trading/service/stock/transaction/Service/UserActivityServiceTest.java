package com.trading.service.stock.transaction.service;

import com.trading.service.stock.transaction.model.Activity;
import com.trading.service.stock.transaction.model.entity.PortfolioData;
import com.trading.service.stock.transaction.model.entity.UserActivity;
import com.trading.service.stock.transaction.model.Stock;
import com.trading.service.stock.transaction.repository.UserActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserActivityServiceTest {
    @Autowired
    UserActivityService userActivityService;
    @MockBean
    UserActivityRepository userActivityRepository;

    PortfolioData portfolioData;
    Stock stock;
    UserActivity userActivity;
    List<UserActivity> userActivityList;
    @BeforeEach
    public void setUp()
    {
        userActivityList=new ArrayList<>();
        portfolioData=new PortfolioData(1,"abc",2.0,2.0,4.0);
        stock=new Stock("abc",1.0,1.0);
        userActivity=new UserActivity(1,"abc","BUY",1.0,1.0,1.0,new Date());
        userActivityList.add(userActivity);
    }


    @Test
    public void getAllUserActivityTest()
    {
        when(userActivityRepository.findAll()).thenReturn(userActivityList);
        assertEquals(userActivityService.getAllUserActivity(),userActivityList);
    }

    @Test
    public void getUserActivityTest()
    {
       when(userActivityRepository.findAllByUserID(1)).thenReturn(userActivityList);
        assertEquals(userActivityService.getUserActivity(1),userActivityList);
    }
}

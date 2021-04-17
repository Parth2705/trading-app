package com.trading.service.stock.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.service.stock.transaction.controller.usercontroller.UserStockTransactionController;
import com.trading.service.stock.transaction.model.Activity;
import com.trading.service.stock.transaction.model.entity.UserActivity;
import com.trading.service.stock.transaction.model.Stock;
import com.trading.service.stock.transaction.service.PortfolioService;
import com.trading.service.stock.transaction.service.UserActivityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserStockTransactionController.class)
public class ControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PortfolioService portfolioService;
    @MockBean
    UserActivityService userActivityService;

    Stock stock;
    @BeforeEach
    void setUp() {
        stock=new Stock("test",1.0,1.0);
    }
    @Test
    public void buyStockTest() throws Exception {
        doNothing().when(portfolioService).addStock(stock,1);
        doNothing().when(userActivityService).addActivity(Activity.BUY,1,stock);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(stock);
        mockMvc.perform( post("/user/stock")
                .sessionAttr("userName","test")
                .sessionAttr("userId",1)
                .sessionAttr("role","USER")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void sellStockTest() throws Exception {
        doNothing().when(portfolioService).sellStock(stock,1);
        doNothing().when(userActivityService).addActivity(Activity.SELL,1,stock);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(stock);
        mockMvc.perform( put("/user/stock")
                .sessionAttr("userName","test")
                .sessionAttr("userId",1)
                .sessionAttr("role","USER")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getActivityTest() throws Exception {

       when(userActivityService.getUserActivity(1)).thenReturn(new ArrayList<UserActivity>());
        mockMvc.perform( get("/user/Stock-activity")
                .sessionAttr("userName","test")
                .sessionAttr("role","USER")
                .sessionAttr("userId",1))
                .andExpect(status().isOk()).andReturn();
    }
}

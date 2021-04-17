package com.trading.service.stock.transaction.filter;

import com.trading.service.stock.transaction.controller.usercontroller.UserStockTransactionController;
import com.trading.service.stock.transaction.service.PortfolioService;

import com.trading.service.stock.transaction.service.UserActivityService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserStockTransactionController.class)
public class SessionValidationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PortfolioService portfolioService;
    @MockBean
    UserActivityService userActivityService;

    @Test
    public void buyStockSessionValidationTest() throws Exception {

        mockMvc.perform(post("/user/stock")
                .sessionAttr("userId",1))
                .andExpect(status().isUnauthorized()).andReturn();
    }
    @Test
    public void adminSessionTest() throws Exception {

        mockMvc.perform(post("/admin/Stock-activity")
                .sessionAttr("role","USER")
                .sessionAttr("userName","test")
                .sessionAttr("userId",1))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void userSessionTest() throws Exception {

        mockMvc.perform(post("/user/Stock-activity")
                .sessionAttr("role","ADMIN")
                .sessionAttr("userName","test")
                .sessionAttr("userId",1))
                .andExpect(status().isUnauthorized()).andReturn();
    }
    @Test
    public void sellStockSessionValidationTest() throws Exception {

        mockMvc.perform( put("/user/stock")
                .sessionAttr("userId",1))
                .andExpect(status().isUnauthorized()).andReturn();
    }
}

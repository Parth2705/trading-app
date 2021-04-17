package com.trading.service.stock.transaction.exception;

import com.trading.service.stock.transaction.controller.usercontroller.UserStockTransactionController;
import com.trading.service.stock.transaction.repository.PortfolioRepository;
import com.trading.service.stock.transaction.service.PortfolioService;
import com.trading.service.stock.transaction.service.UserActivityService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserStockTransactionController.class)
public class GlobalExceptionHandlerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PortfolioService portfolioService;
    @MockBean
    UserActivityService userActivityService;
    @MockBean
    PortfolioRepository portfolioRepository;

    @Test
    public void EntityNotFoundTest() throws Exception {
        when(userActivityService.getUserActivity(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform( get("/user/stock-activity")
                .sessionAttr("userName","test")
                .sessionAttr("role","USER")
                .sessionAttr("userId",1))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void nullPointerExceptionTest() throws Exception {
        when(userActivityService.getUserActivity(1)).thenThrow(new NullPointerException());
        mockMvc.perform( get("/user/Stock-activity")
                .sessionAttr("userName","test")
                .sessionAttr("role","USER")
                .sessionAttr("userId",1))
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void GeneralExceptionTest() throws Exception {
        when(userActivityService.getUserActivity(1)).thenThrow(new NoSuchElementException());
        mockMvc.perform( get("/user/Stock-activity")
                .sessionAttr("userName","test")
                .sessionAttr("role","USER")
                .sessionAttr("userId",1))
                .andExpect(status().isBadRequest()).andReturn();
    }
}

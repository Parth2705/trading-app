package com.trading.service.stock.transaction.service;


import com.trading.service.stock.transaction.model.entity.PortfolioData;
import com.trading.service.stock.transaction.model.Stock;
import com.trading.service.stock.transaction.repository.PortfolioRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PortfolioServiceTest {
    @Autowired
    PortfolioService portfolioService;
    @MockBean
    PortfolioRepository portfolioRepository;

    PortfolioData portfolioData;
    Stock stock;

    @BeforeEach
    public void setUp()
    {
        portfolioData=new PortfolioData(1,"abc",3.0,2.0,6.0);
        stock=new Stock("abc",1.0,1.0);

    }
    @Test
    public void addExistingStockTest()
    {
       when(portfolioRepository.findByUserIdAndStockSymbol(1,"abc")).thenReturn(Optional.ofNullable(portfolioData));
       assertEquals(Optional.ofNullable(portfolioService.addStock(stock, 1).getQuantity()),Optional.ofNullable(4.0));
    }

    @Test
    public void addNewStockTest()
    {
        when(portfolioRepository.findByUserIdAndStockSymbol(1,"abc")).thenReturn(Optional.ofNullable(null));
        assertEquals(Optional.ofNullable(portfolioService.addStock(stock, 1).getQuantity()),Optional.ofNullable(1.0));
    }

    @Test
    public void sellStockTest()
    {
        when(portfolioRepository.findByUserIdAndStockSymbol(1,"abc")).thenReturn(Optional.ofNullable(portfolioData));
        assertEquals(Optional.ofNullable(portfolioService.sellStock(stock, 1).getQuantity()),Optional.ofNullable(2.0));
    }
}

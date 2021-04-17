package com.trading.service.stocks.fetch.exception;

import com.trading.service.stocks.fetch.controller.StocksFetchNonStreamController;
import com.trading.service.stocks.fetch.model.PortfolioListing;
import com.trading.service.stocks.fetch.model.StocksListing;
import com.trading.service.stocks.fetch.model.StocksWatchlist;
import com.trading.service.stocks.fetch.service.StocksListingService;
import com.trading.service.stocks.fetch.service.StocksPortfolioListingService;
import com.trading.service.stocks.fetch.service.impl.StockListingServiceStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StocksFetchNonStreamController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    @Qualifier("stocksListingServiceNonStream")
    StocksListingService stockListingService;

    @MockBean
    @Qualifier("stocksPortfolioServiceNonStream")
    StocksPortfolioListingService stocksPortfolioService;

    @MockBean
    @Qualifier("stocksPortfolioServiceStream")
    StocksPortfolioListingService stocksPortfolioServiceStream;

    @MockBean
    StockListingServiceStream stockListingServiceStream;

    List<StocksListing> stockListings;
    List<PortfolioListing> portfolioListings;
    List<StocksWatchlist> watchList;
    @BeforeEach
    void setUp() {
        stockListings=new ArrayList<>();
        portfolioListings=new ArrayList<>();
        watchList=new ArrayList<>();
    }

    @Test
    public void nullPointerExceptionTest() throws Exception {
        when(stockListingService.publishStocksListing()).thenThrow(new NullPointerException());
        mockMvc.perform( get("/stocks/stock-listing")
                .sessionAttr("userName","test"))
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void entityNotFoundExceptionTest() throws Exception {
        when(stockListingService.publishStocksListing()).thenThrow(new EntityNotFoundException());
        mockMvc.perform( get("/stocks/stock-listing")
                .sessionAttr("userName","test"))
                .andExpect(status().isNotFound()).andReturn();
    }
}

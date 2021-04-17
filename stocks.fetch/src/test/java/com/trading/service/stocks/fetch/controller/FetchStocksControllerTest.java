package com.trading.service.stocks.fetch.controller;



import com.trading.service.stocks.fetch.model.PortfolioListing;
import com.trading.service.stocks.fetch.model.StocksListing;
import com.trading.service.stocks.fetch.model.StocksWatchlist;
import com.trading.service.stocks.fetch.service.StocksListingService;
import com.trading.service.stocks.fetch.service.StocksPortfolioListingService;
import com.trading.service.stocks.fetch.service.impl.StockListingServiceStream;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@WebMvcTest(StocksFetchNonStreamController.class)
public class FetchStocksControllerTest {


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
    public void getStockListTest() throws Exception {
        when(stockListingService.publishStocksListing()).thenReturn(stockListings);
        mockMvc.perform( get("/stocks/stock-listing"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void getStockBySymbol() throws Exception {
        when(stockListingService.getStockBySymbol("symbol")).thenReturn(new StocksListing());
        mockMvc.perform( get("/stocks/stock-listing/symbol"))
                .andExpect(status().isUnauthorized()).andReturn();
    }



    @Test
    public void getStockPortfolio() throws Exception {
        when(stocksPortfolioService.publishStocksPortfolio(1)).thenReturn(portfolioListings);
        mockMvc.perform( get("/stocks/portfolio"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void getStockPortfolioBySymbol() throws Exception {
        when(stocksPortfolioService.getStockPortfolioBySymbol(1,"symbol"))
                .thenReturn(new PortfolioListing());
        mockMvc.perform( get("/stocks/portfolio/symbol"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void setUserWatchList() throws Exception {
        doNothing().when(stocksPortfolioService).updateUserWatchList(1,"symbol",true);
        mockMvc.perform( get("/stocks/wish-list"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void getUserWatchList() throws Exception {
        when(stocksPortfolioService.publishUserWatchList(1))
                .thenReturn(watchList);
        mockMvc.perform( get("/stocks/wish-list"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

}

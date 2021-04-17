package com.trading.service.stocks.fetch.service;

import com.trading.service.stocks.fetch.client.StocksApiClient;
import com.trading.service.stocks.fetch.model.PortfolioListing;
import com.trading.service.stocks.fetch.model.Stocks;
import com.trading.service.stocks.fetch.model.StocksWatchlist;
import com.trading.service.stocks.fetch.model.entity.StocksEntity;
import com.trading.service.stocks.fetch.model.entity.StocksPortfolioEntity;
import com.trading.service.stocks.fetch.model.entity.StocksWatchListEntity;
import com.trading.service.stocks.fetch.repository.StocksPortfolioRepositoryImpl;
import com.trading.service.stocks.fetch.repository.StocksRepository;
import com.trading.service.stocks.fetch.repository.StocksWatchListRepository;
import com.trading.service.stocks.fetch.repository.StocksWatchListRepositoryImpl;
import com.trading.service.stocks.fetch.service.impl.StockListingServiceStream;
import com.trading.service.stocks.fetch.service.impl.StocksPortfolioServiceNonStream;
import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class StocksPortfolioServiceNonStreamTest {

    @Autowired
    StocksPortfolioServiceNonStream stocksPortfolioServiceNonStream;

    @MockBean
    StockListingServiceStream stockListingService;

    @MockBean
    StocksWatchListRepository stocksWatchListRepository;

    @MockBean
    StocksRepository stocksRepository;

    @MockBean
    StocksPortfolioRepositoryImpl portfolioRepository;

    @MockBean
    StocksApiClient stocksApiCLient;

    @MockBean
    StocksWatchListRepositoryImpl watchListRepository;

    List<StocksEntity> stocksEntityListings;
    StocksEntity stocksEntity;
    StocksPortfolioEntity stocksPortfolioEntity;
    List<StocksPortfolioEntity> stocksPortfolioEntityList;
    Observable<Stocks> stocksObservable;
    List<Stocks> stocksList;
    PortfolioListing portfolioListing;
    List<StocksWatchListEntity> stocksWatchListEntities;
    StocksWatchListEntity stocksWatchListEntity;
    StocksWatchlist stocksWatchlist;

    @BeforeEach
    void setUp() {
        stocksEntityListings = new ArrayList<>();
        stocksPortfolioEntityList=new ArrayList<>();
        stocksWatchListEntities=new ArrayList<>();
        stocksList=new ArrayList<>();
        stocksList.add(new Stocks("symbol","stockName",new BigDecimal("2.00")));
        stocksEntity=new StocksEntity(1,"stockName","symbol",22);
        stocksEntityListings.add(stocksEntity);
        stocksObservable= Observable.fromIterable(stocksList);
        stocksPortfolioEntity=new StocksPortfolioEntity(1,1,"symbol",2,22,44);
        stocksPortfolioEntityList.add(stocksPortfolioEntity);
        portfolioListing=new PortfolioListing("stockName","symbol",22.0,2.0,-90.9090909090909,true,2,44.0,-40.0);
        stocksWatchListEntity=new StocksWatchListEntity(1,"symbol",1,0);
        stocksWatchListEntities.add(stocksWatchListEntity);
        stocksWatchlist=new StocksWatchlist("stockName","symbol",2.0,2,-90.9090909090909,true);
    }

    @Test
    public void publishStocksPortfolioTest() throws Exception {
        when(stocksRepository.findAll()).thenReturn(stocksEntityListings);
        when(stocksApiCLient.getStockListing()).thenReturn(stocksObservable);
        when(portfolioRepository.getUserStockPortfolioList(1)).thenReturn(stocksPortfolioEntityList.stream());
        assertEquals(stocksPortfolioServiceNonStream.publishStocksPortfolio(1).stream().findFirst().get(),portfolioListing);     ;

    }

    @Test
    public void getStockPortfolioBySymbol() throws Exception {
        when(stocksRepository.findAll()).thenReturn(stocksEntityListings);
        when(stocksApiCLient.getStockListing()).thenReturn(stocksObservable);
        when(portfolioRepository.getUserStockPortfolioList(1)).thenReturn(stocksPortfolioEntityList.stream());
        assertEquals(stocksPortfolioServiceNonStream.getStockPortfolioBySymbol(1,"symbol"),portfolioListing);     ;

    }

    @Test
    public void publishUserWatchList() throws Exception {
        when(stocksRepository.findAll()).thenReturn(stocksEntityListings);
        when(stocksApiCLient.getStockListing()).thenReturn(stocksObservable);
        when(portfolioRepository.getUserStockPortfolioList(1)).thenReturn(stocksPortfolioEntityList.stream());
        when(watchListRepository.getUserWatchListNonStream(1)).thenReturn(stocksWatchListEntities);
        assertEquals(stocksPortfolioServiceNonStream.publishUserWatchList(1).stream().findFirst().get(),stocksWatchlist);     ;

    }
}

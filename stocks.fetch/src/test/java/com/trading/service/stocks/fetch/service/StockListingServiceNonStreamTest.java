package com.trading.service.stocks.fetch.service;

import com.trading.service.stocks.fetch.client.StocksApiClient;

import com.trading.service.stocks.fetch.model.Stocks;
import com.trading.service.stocks.fetch.model.StocksListing;

import com.trading.service.stocks.fetch.model.entity.StocksEntity;

import com.trading.service.stocks.fetch.repository.StocksRepository;
import com.trading.service.stocks.fetch.service.impl.StockListingServiceStream;
import com.trading.service.stocks.fetch.service.impl.StocksListingServiceNonStream;
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
public class StockListingServiceNonStreamTest {

    @Autowired
    StocksListingServiceNonStream stockListingService;

    @MockBean
    StockListingServiceStream stockListingServiceStream;

    @MockBean
    StocksRepository stocksRepository;

    @MockBean
    StocksApiClient stocksApiCLient;

    List<StocksEntity> stocksEntityListings;
    List<Stocks> stocksList;
    Observable<Stocks> stocksObservable;
    List<StocksListing> stocksListingList;
    StocksListing stocksListing;
    StocksEntity stocksEntity;

    @BeforeEach
    void setUp() {
        stocksEntityListings =new ArrayList<>();
        stocksList=new ArrayList<>();
        stocksListingList=new ArrayList<>();
        stocksEntity=new StocksEntity(1,"stockName","s",100);
        stocksListing=new StocksListing("stockName","s",2,-98.0);
        stocksList.add(new Stocks("s","stockName",new BigDecimal("2.00")));
        stocksObservable= Observable.fromIterable(stocksList);
        stocksListingList.add(stocksListing);
        stocksEntityListings.add(stocksEntity);

    }

    @Test
    public void publishStocksListingTest() throws Exception {
        when(stocksRepository.findAll()).thenReturn(stocksEntityListings);
        when(stocksApiCLient.getStockListing()).thenReturn(stocksObservable);
        assertEquals(stockListingService.publishStocksListing(),stocksListingList);

    }

    @Test
    public void getStockBySymbol() throws Exception {
        when(stocksRepository.findBySymbol("s")).thenReturn(stocksEntity);
        when(stocksApiCLient.getStockListing()).thenReturn(stocksObservable);
        assertEquals(stockListingService.getStockBySymbol("s"),stocksListing);
    }
}

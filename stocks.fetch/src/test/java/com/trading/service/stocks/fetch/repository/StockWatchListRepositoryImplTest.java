package com.trading.service.stocks.fetch.repository;

import com.trading.service.stocks.fetch.model.entity.StocksWatchListEntity;
import com.trading.service.stocks.fetch.service.impl.StockListingServiceStream;
import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class StockWatchListRepositoryImplTest {

    @MockBean
    StocksWatchListRepository stocksWatchListRepository;

    @MockBean
    StockListingServiceStream stockListingServiceStream;

    @Autowired
    StocksWatchListRepositoryImpl stocksWatchListRepositoryImpl;

    StocksWatchListEntity stocksWatchListEntity;
    List<StocksWatchListEntity> stocksWatchListEntityList;

    @BeforeEach
    void setUp() {
        stocksWatchListEntityList=new ArrayList<>();
        stocksWatchListEntity=new StocksWatchListEntity(1,"symbol",1,22);
        stocksWatchListEntityList.add(stocksWatchListEntity);
    }

    @Test
    public void publishStocksListingTest() throws Exception {
        when(stocksWatchListRepository.findByUserId(1)).thenReturn(stocksWatchListEntityList);
        assertEquals(stocksWatchListRepositoryImpl.getUserWatchList(1).blockingFirst(), Observable.fromIterable(stocksWatchListEntityList).blockingFirst());

    }
}

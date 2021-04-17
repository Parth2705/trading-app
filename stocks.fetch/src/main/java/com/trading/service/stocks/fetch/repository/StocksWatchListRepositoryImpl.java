package com.trading.service.stocks.fetch.repository;

import com.trading.service.stocks.fetch.model.entity.StocksWatchListEntity;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Slf4j

public class StocksWatchListRepositoryImpl  {

    @Autowired
    StocksWatchListRepository stocksWatchListRepository;

    public Observable<StocksWatchListEntity> getUserWatchList(int userId){
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        return Observable.fromIterable(stocksWatchListRepository.findByUserId(userId))
                .switchIfEmpty(Observable.empty());
    }

    public List<StocksWatchListEntity> getUserWatchListNonStream(int userId){
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        return stocksWatchListRepository.findByUserId(userId);
    }
}

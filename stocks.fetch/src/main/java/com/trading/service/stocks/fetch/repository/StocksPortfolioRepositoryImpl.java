package com.trading.service.stocks.fetch.repository;

import com.trading.service.stocks.fetch.model.entity.StocksPortfolioEntity;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class StocksPortfolioRepositoryImpl  {

    @Autowired
    StocksPortfolioRepository stocksPortfolioRepository;

    public Observable<StocksPortfolioEntity> getUserStockPortfolio(int userId){
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        return Observable.fromIterable(stocksPortfolioRepository.findByUserId(userId))
                .switchIfEmpty(Observable.empty());
    }

    public Stream<StocksPortfolioEntity> getUserStockPortfolioList(int userId){
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        return stocksPortfolioRepository.findByUserId(userId).stream();
    }
}

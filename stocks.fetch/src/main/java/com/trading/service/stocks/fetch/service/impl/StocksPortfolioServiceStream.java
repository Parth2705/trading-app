package com.trading.service.stocks.fetch.service.impl;


import com.trading.service.stocks.fetch.client.StocksApiClient;
import com.trading.service.stocks.fetch.model.PortfolioListing;
import com.trading.service.stocks.fetch.model.Stocks;
import com.trading.service.stocks.fetch.model.StocksWatchlist;
import com.trading.service.stocks.fetch.model.entity.StocksEntity;
import com.trading.service.stocks.fetch.model.entity.StocksPortfolioEntity;
import com.trading.service.stocks.fetch.model.entity.StocksWatchListEntity;
import com.trading.service.stocks.fetch.repository.*;
import com.trading.service.stocks.fetch.service.StocksPortfolioListingService;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class StocksPortfolioServiceStream implements StocksPortfolioListingService {

    @Autowired
    StocksWatchListRepository stocksWatchListRepository;

    @Autowired
    StocksRepository stocksRepository;

    @Autowired
    StocksPortfolioRepositoryImpl spr;

    @Autowired
    StocksApiClient stocksApiCLient;

    @Autowired
    StocksWatchListRepositoryImpl watchListRepository;


    public Observable<PortfolioListing> publishStocksPortfolio(int userId) {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);

        Observable<StocksPortfolioEntity> observable = spr.getUserStockPortfolio(userId);
        List<StocksEntity> se = stocksRepository.findAll();

        if (observable != null) {
            return Observable.interval(10, TimeUnit.SECONDS).flatMap(
                    a -> observable.map(
                            spe -> {
                                Stocks stocks1 = stocksApiCLient.getStockListing()
                                        .filter(s -> s.getSymbol().equalsIgnoreCase(spe.getStockSymbol())).blockingSingle();
                                StocksEntity stocksEntity = se.stream()
                                        .filter(stocksEntity1 -> stocksEntity1.getStockSymbol().equalsIgnoreCase(spe.getStockSymbol()))
                                        .findFirst()
                                        .get();
                                return new com.trading.service.stocks.fetch.model.PortfolioListing(stocks1.getName(), stocks1.getSymbol(),
                                        spe.getAveragePrice(),
                                        stocks1.getLastSale().doubleValue(),
                                        ((Math.abs(stocks1.getLastSale().doubleValue()) - stocksEntity.getPrice()) / stocksEntity.getPrice() * 100),
                                        Math.abs(stocks1.getLastSale().doubleValue()) < stocksEntity.getPrice() ? true : false,
                                        spe.getQuantity(),
                                        spe.getTotalEquity(),
                                        ((spe.getQuantity() * stocks1.getLastSale().doubleValue()) - spe.getTotalEquity())
                                );
                            }
                    )
            ).switchIfEmpty(Observable.empty());
        } else {
            throw new EntityNotFoundException(this.getClass() + "."
                    + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public Observable<PortfolioListing> getStockPortfolioBySymbol(int userId, String symbol) {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);

        Observable<StocksPortfolioEntity> observable = spr.getUserStockPortfolio(userId)
                .filter(stocksPortfolioEntity -> stocksPortfolioEntity.getStockSymbol().equalsIgnoreCase(symbol));

        List<StocksEntity> se = stocksRepository.findAll();

        if (!observable.isEmpty().blockingGet()) {
            return Observable.interval(10, TimeUnit.SECONDS).flatMap(
                    a -> observable.map(
                            spe -> {
                                Stocks stocks1 = stocksApiCLient.getStockListing()
                                        .filter(s -> s.getSymbol().equalsIgnoreCase(spe.getStockSymbol())).blockingSingle();

                                StocksEntity stocksEntity = se.stream()
                                        .filter(stocksEntity1 -> stocksEntity1.getStockSymbol().equalsIgnoreCase(spe.getStockSymbol()))
                                        .findFirst()
                                        .get();
                                return new com.trading.service.stocks.fetch.model.PortfolioListing(stocks1.getName(), stocks1.getSymbol(),
                                        spe.getAveragePrice(),
                                        stocks1.getLastSale().doubleValue(),
                                        ((Math.abs(stocks1.getLastSale().doubleValue()) - stocksEntity.getPrice()) / stocksEntity.getPrice() * 100),
                                        Math.abs(stocks1.getLastSale().doubleValue()) < stocksEntity.getPrice() ? true : false,
                                        spe.getQuantity(),
                                        spe.getTotalEquity(),
                                        ((spe.getQuantity() * stocks1.getLastSale().doubleValue()) - spe.getTotalEquity())
                                );
                            }
                    )
            ).switchIfEmpty(Observable.empty());
        } else {
            throw new EntityNotFoundException(this.getClass() + "."
                    + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public Observable<StocksWatchlist> publishUserWatchList(int userId) {

        log.info("Inside " + getClass() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        Observable<StocksPortfolioEntity> portfolioEntityObservable = spr.getUserStockPortfolio(userId);
        Observable<StocksWatchListEntity> watchListEntityObservable = watchListRepository.getUserWatchList(userId);
        List<StocksEntity> se = stocksRepository.findAll();

        if (portfolioEntityObservable != null && watchListEntityObservable != null) {
            return Observable.interval(10, TimeUnit.SECONDS).flatMap(
                    a -> watchListEntityObservable.map(swlEntity ->
                    {
                        Stocks stocks = stocksApiCLient.getStockListing()
                                .filter(s -> s.getSymbol().equalsIgnoreCase(swlEntity.getStockSymbol()))
                                .blockingSingle();

                        StocksPortfolioEntity spEntity = portfolioEntityObservable
                                .filter(s -> s.getStockSymbol().equalsIgnoreCase(swlEntity.getStockSymbol()))
                                .switchIfEmpty(Observable.empty()).firstElement()
                                .blockingGet();

                        StocksEntity sEntity = se.stream().filter(stocksEntity ->
                                stocksEntity.getStockSymbol().equalsIgnoreCase(swlEntity.getStockSymbol())).findFirst()
                                .orElseThrow(() -> new EntityNotFoundException());
                        if (spEntity != null)
                            return new StocksWatchlist(
                                    stocks.getName(),
                                    stocks.getSymbol(),
                                    stocks.getLastSale().doubleValue(),
                                    spEntity.getQuantity(),
                                    ((Math.abs(stocks.getLastSale().doubleValue()) - sEntity.getPrice()) / sEntity.getPrice() * 100),
                                    true
                            );
                        else
                            return new StocksWatchlist(
                                    stocks.getName(),
                                    stocks.getSymbol(),
                                    stocks.getLastSale().doubleValue(),
                                    0,
                                    ((Math.abs(stocks.getLastSale().doubleValue()) - sEntity.getPrice()) / sEntity.getPrice() * 100),
                                    true
                            );
                    })
            ).switchIfEmpty(Observable.empty());
        } else {
            return null;
        }
    }

    @Override
    public void updateUserWatchList(int userId, String symbol, boolean watching) {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        StocksWatchListEntity stocksWatchListEntity = stocksWatchListRepository.findByUserIdAndSymbol(userId, symbol);
        if (watching) {
            if (stocksWatchListEntity == null) {
                stocksWatchListEntity = stocksWatchListRepository
                        .save(new StocksWatchListEntity(symbol, userId, 0));

            } else {
                stocksWatchListEntity.setStockSymbol(symbol);
                stocksWatchListEntity.setUserId(userId);
                stocksWatchListEntity.setAlertPrice(stocksWatchListEntity.getAlertPrice());
                stocksWatchListRepository.save(stocksWatchListEntity);
            }
        } else {
            if (stocksWatchListEntity != null) {
                stocksWatchListRepository.deleteById(stocksWatchListEntity.getWatchListId());
            }
        }

    }
}

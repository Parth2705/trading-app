package com.trading.service.stocks.fetch.service.impl;

import com.trading.service.stocks.fetch.model.Stocks;
import com.trading.service.stocks.fetch.model.StocksListing;
import com.trading.service.stocks.fetch.model.entity.StocksEntity;
import com.trading.service.stocks.fetch.repository.StocksRepository;
import com.trading.service.stocks.fetch.client.StocksApiClient;

import com.trading.service.stocks.fetch.service.StocksListingService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StockListingServiceStream implements StocksListingService {

    @Autowired
    StocksRepository stocksRepository;

    @Autowired
    StocksApiClient stocksApiCLient;


    public void updateStocks() {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() );
        stocksApiCLient.getStockListing().forEach(stocks -> {
            StocksEntity se = stocksRepository.findBySymbol(stocks.getSymbol());
            if (se == null) {
                se = stocksRepository.save(new StocksEntity(stocks.getName(), stocks.getSymbol(), stocks.getLastSale().doubleValue()));
            } else {
                se.setStockName(stocks.getName());
                se.setStockSymbol(stocks.getSymbol());
                se.setPrice(Math.abs(stocks.getLastSale().doubleValue()));
                stocksRepository.save(se);
            }
        });

    }


    public Observable<StocksListing> publishStocksListing() {

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() );
        List<StocksEntity> se = stocksRepository.findAll();


        return Observable.interval(10, TimeUnit.SECONDS).flatMap(aLong ->
                {
                    List<StocksListing> list = new ArrayList<>();
                    Observable.fromIterable(se).forEach(spe -> {
                        Stocks stock = stocksApiCLient.getStockListing().filter(s -> s.getSymbol().equalsIgnoreCase(spe.getStockSymbol())).blockingSingle();
                        list.add(new StocksListing(stock.getName(), stock.getSymbol(),
                                Math.abs(stock.getLastSale().doubleValue()),
                                ((Math.abs(stock.getLastSale().doubleValue()) - spe.getPrice()) / spe.getPrice() * 100)

                        ));
                    });
                    return Observable.fromIterable(list);

                }

        );
    }



    public Observable<StocksListing> getStockBySymbol(String symbol) {

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() );
        StocksEntity se = stocksRepository.findBySymbol(symbol);

        if (se != null) {
            return Observable.interval(10, TimeUnit.SECONDS).flatMap(aLong ->
                    {
                        return Maybe.just(se).map(spe -> {
                            Stocks stock = stocksApiCLient.getStockListing()
                                    .filter(s -> s.getSymbol().equalsIgnoreCase(spe.getStockSymbol()))
                                    .blockingSingle();
                            return (new StocksListing(stock.getName(), stock.getSymbol(),
                                    Math.abs(stock.getLastSale().doubleValue()),
                                    ((Math.abs(stock.getLastSale().doubleValue()) - spe.getPrice()) / spe.getPrice() * 100)
                            ));
                        }).toObservable();
                    }
            ).switchIfEmpty(Observable.empty());
        } else {
            return null;
        }
    }


}
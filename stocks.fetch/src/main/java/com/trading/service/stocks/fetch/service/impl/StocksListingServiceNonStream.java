package com.trading.service.stocks.fetch.service.impl;

import com.trading.service.stocks.fetch.client.StocksApiClient;
import com.trading.service.stocks.fetch.model.Stocks;
import com.trading.service.stocks.fetch.model.StocksListing;
import com.trading.service.stocks.fetch.model.entity.StocksEntity;
import com.trading.service.stocks.fetch.repository.StocksPortfolioRepository;
import com.trading.service.stocks.fetch.repository.StocksPortfolioRepositoryImpl;
import com.trading.service.stocks.fetch.repository.StocksRepository;
import com.trading.service.stocks.fetch.service.StocksListingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StocksListingServiceNonStream implements StocksListingService {

    @Autowired
    StocksRepository stocksRepository;

    @Autowired
    StocksPortfolioRepository stocksPortfolioRepository;

    @Autowired
    StocksPortfolioRepositoryImpl spr;

    @Autowired
    StocksApiClient stocksApiCLient;

    @Override
    public void updateStocks() {

    }

    @Override
    public List<StocksListing> publishStocksListing() {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() );
        List<StocksEntity> se = stocksRepository.findAll();

        List<StocksListing> list = se.stream().map(
                spe -> {
                    Stocks stock = stocksApiCLient.getStockListing()
                            .filter(s -> s.getSymbol().equalsIgnoreCase(spe.getStockSymbol())).blockingSingle();
                    return new StocksListing(stock.getName(), stock.getSymbol(),
                            Math.abs(stock.getLastSale().doubleValue()),
                            ((Math.abs(stock.getLastSale().doubleValue()) - spe.getPrice()) / spe.getPrice() * 100)

                    );

                }
        ).collect(Collectors.toList());
        System.out.println("\n\n"+list+"\n\n");
        return list;
    }

    @Override
    public StocksListing getStockBySymbol(String symbol) {

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName());
        StocksEntity se = stocksRepository.findBySymbol(symbol);

        if (se != null) {
            Stocks stock = stocksApiCLient.getStockListing()
                    .filter(s -> s.getSymbol().equalsIgnoreCase(se.getStockSymbol()))
                    .blockingSingle();
            return new StocksListing(stock.getName(), stock.getSymbol(),
                    Math.abs(stock.getLastSale().doubleValue()),
                    ((Math.abs(stock.getLastSale().doubleValue()) - se.getPrice()) / se.getPrice() * 100)
            );

        } else {
            return null;
        }

    }


}

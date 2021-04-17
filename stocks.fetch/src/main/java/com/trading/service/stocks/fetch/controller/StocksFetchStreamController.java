package com.trading.service.stocks.fetch.controller;

import com.trading.service.stocks.fetch.model.PortfolioListing;
import com.trading.service.stocks.fetch.model.StocksListing;
import com.trading.service.stocks.fetch.model.StocksWatchlist;
import com.trading.service.stocks.fetch.model.Watching;
import com.trading.service.stocks.fetch.service.StocksListingService;
import com.trading.service.stocks.fetch.service.StocksPortfolioListingService;
import com.trading.service.stocks.fetch.service.impl.StockListingServiceStream;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/stocks/stream")
public class StocksFetchStreamController {

    @Autowired
    @Qualifier("stockListingServiceStream")
    StocksListingService stockListingService;

    @Autowired
    @Qualifier("stocksPortfolioServiceStream")
    StocksPortfolioListingService stocksPortfolioServiceStream;

    @GetMapping(value = "/stock-listing", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<StocksListing> getStocksListing(HttpSession session) {

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + session.getAttribute("userId"));
        return stockListingService.publishStocksListing();
    }

    @GetMapping(value = "/stock-listing/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<StocksListing> getStockBySymbol(@PathVariable("symbol") String symbol, HttpSession session) {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName());
        return stockListingService.getStockBySymbol(symbol);
    }

    @GetMapping(value = "/portfolio", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<PortfolioListing> getStockPortfolio(HttpSession session) {
        //final int userId = (int) session.getAttribute("userId");
        final int userId =4;
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        return stocksPortfolioServiceStream.publishStocksPortfolio(userId);
    }

    @GetMapping(value = "/portfolio/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<PortfolioListing> getStockPortfolio(@PathVariable("symbol") String symbol, HttpSession session) {
        final int userId = 4;//(int) session.getAttribute("userId");

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        return stocksPortfolioServiceStream.getStockPortfolioBySymbol(userId, symbol);
    }

    @PutMapping(value = "/wish-list")
    public ResponseEntity<Void> getStockPortfolio(@RequestBody Watching watching, HttpSession session) {
        final int userId = 4;//(int) session.getAttribute("userId");
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);

        stocksPortfolioServiceStream.updateUserWatchList(userId, watching.getSymbol(), watching.isWatching());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "wish-list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<StocksWatchlist> getUserWatchList(HttpSession session) {
        final int userId = 4;//(int) session.getAttribute("userId");

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        return stocksPortfolioServiceStream.publishUserWatchList(userId);
    }

}

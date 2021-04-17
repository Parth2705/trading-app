package com.trading.service.stocks.fetch.controller;

import com.trading.service.stocks.fetch.model.*;
import com.trading.service.stocks.fetch.service.StocksListingService;
import com.trading.service.stocks.fetch.service.StocksPortfolioListingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stocks")
public class StocksFetchNonStreamController {

    @Autowired
    @Qualifier("stocksListingServiceNonStream")
    StocksListingService stockListingService;

    @Autowired
    @Qualifier("stocksPortfolioServiceNonStream")
    StocksPortfolioListingService userStockService;

    @GetMapping( "/stock-listing")
    public ResponseEntity<List<StocksListing>> getStocksListing(HttpSession session) {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName());

        List<StocksListing> stocksListingList = stockListingService.publishStocksListing();
        return new ResponseEntity<>(stocksListingList, HttpStatus.OK);
    }

    @GetMapping("/stock-listing/{symbol}")
    public ResponseEntity<StocksListing> getStocksListingBySymbol(@PathVariable("symbol") String symbol, HttpSession session) {
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName());

        StocksListing stocksListing = stockListingService.getStockBySymbol(symbol);
        if (stocksListing==null)
            return new ResponseEntity<>(stocksListing, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(stocksListing, HttpStatus.OK);
    }

    @GetMapping( "/portfolio")
    public ResponseEntity<List<PortfolioListing>> getStockPortfolio(HttpSession session) {
        final int userId = (int) session.getAttribute("userId");

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        List<PortfolioListing> portfolio= userStockService.publishStocksPortfolio(userId);
        return new ResponseEntity<>(portfolio,HttpStatus.OK);
    }

    @GetMapping(value = "/portfolio/{symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PortfolioListing> getStockPortfolioBySymbol(@PathVariable("symbol") String symbol, HttpSession session) {
        final int userId = (int) session.getAttribute("userId");

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        PortfolioListing portfolio= userStockService.getStockPortfolioBySymbol(userId, symbol);
        return  new ResponseEntity<>(portfolio,HttpStatus.OK);
    }

    @PutMapping(value = "/wish-list")
    public ResponseEntity<Void> addUserWishList(@RequestBody Watching watching, HttpSession session) {
        final int userId = (int) session.getAttribute("userId");
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        userStockService.updateUserWatchList(userId, watching.getSymbol(), watching.isWatching());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/wish-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StocksWatchlist>> getUserWatchList(HttpSession session) {

        final int userId = (int) session.getAttribute("userId");
        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: " + userId);
        List<StocksWatchlist> watchList= userStockService.publishUserWatchList(userId);
        return new ResponseEntity<>(watchList, HttpStatus.OK);
    }

}

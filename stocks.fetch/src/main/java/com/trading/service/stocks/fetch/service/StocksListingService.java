package com.trading.service.stocks.fetch.service;


public interface StocksListingService {


    public abstract void updateStocks();

    public abstract <T> T publishStocksListing();
//    public abstract Observable<StocksListing> publishStocksListing(String a);

    public abstract <T> T getStockBySymbol(String symbol);
}

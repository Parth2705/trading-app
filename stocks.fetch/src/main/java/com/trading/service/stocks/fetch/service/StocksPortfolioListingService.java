package com.trading.service.stocks.fetch.service;


public interface StocksPortfolioListingService {

    public abstract <T> T publishStocksPortfolio(int userId);

    public abstract  <T> T getStockPortfolioBySymbol(int userId, String symbol) ;

    public abstract <T> T publishUserWatchList(int userId);


    public void updateUserWatchList(int userId, String symbol, boolean watching);
}

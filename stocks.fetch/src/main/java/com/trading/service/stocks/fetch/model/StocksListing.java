package com.trading.service.stocks.fetch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StocksListing {
    private String stockName;
    private String stockSymbol;
    private double price;
    private double movement;

    public StocksListing() {

    }

}

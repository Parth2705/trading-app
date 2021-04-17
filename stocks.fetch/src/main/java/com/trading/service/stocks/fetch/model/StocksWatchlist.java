package com.trading.service.stocks.fetch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StocksWatchlist {
    private String name;
    private String symbol;
    private Double price;
    private int quantity;
    private double movement;
    private boolean watching;
}

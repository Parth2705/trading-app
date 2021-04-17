package com.trading.service.stocks.fetch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioListing {
    private String stockName;
    private String stockSymbol;
    private double averagePrice;
    private double currentPrice;
    private double movementToday;
    private boolean movementFlag;
    private int quantity;
    private double totalEquity;
    private double returnEquity;

    public PortfolioListing() {

    }
}

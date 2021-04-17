package com.trading.service.stocks.fetch.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "user_watchlist")
public class StocksWatchListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watch_list_id")
    private int watchListId;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    @Column(name = "user_id")
    private int userId;

    @Column(name = "price_alert")
    private double alertPrice;

    public StocksWatchListEntity(String stock_symbol, int user_id, double alert_price) {
        this.stockSymbol = stock_symbol;
        this.userId = user_id;
        this.alertPrice = alert_price;
    }
    public StocksWatchListEntity(){

    }
}

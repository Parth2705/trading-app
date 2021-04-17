package com.trading.service.stocks.fetch.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Data
@Table(name = "stocks")
public class StocksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private int id;
    @Column(name = "stock_name")
    private String stockName;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    @Column(name = "price")
    private double price;

    public StocksEntity() {
    }


    public StocksEntity(String stockName, String stockSymbol, double price) {
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
        this.price = price;
    }
}

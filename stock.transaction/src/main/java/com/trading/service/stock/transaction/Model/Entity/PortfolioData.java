package com.trading.service.stock.transaction.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user_portfolio")
public class PortfolioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    int id;
    @Column(name="User_id")
    int userId;
    @Column(name="Stock_symbol")
    String stockSymbol;
    @Column(name="quantity")
    Double quantity;
    @Column(name="Average_price")
    Double averagePrice;
    @Column(name="Total_Equity")
    Double totalEquity;

    public PortfolioData(int userId, String stockSymbol, Double quantity, Double averagePrice, Double totalEquity) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.totalEquity = totalEquity;
    }
}

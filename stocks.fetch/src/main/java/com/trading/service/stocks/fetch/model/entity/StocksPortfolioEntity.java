package com.trading.service.stocks.fetch.model.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_portfolio")
public class StocksPortfolioEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int spId;
    @Column(name = "userId")
    private int userId;
    @Column(name = "stock_symbol")
    private String stockSymbol;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "averagePrice")
    private double averagePrice;
    @Column(name = "total_equity")
    private double totalEquity;


}

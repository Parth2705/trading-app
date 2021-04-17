package com.trading.service.stocks.fetch.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "user_stock_activity")
public class UserStockActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    int id;
    @Column(name="User_id")
    int userID;
    @Column(name="Stock_symbol")
    String stockSymbol;
    @Column(name="Activity")
    String activity;
    @Column(name="quantity")
    Double quantity;
    @Column(name="Price_per_Stock")
    Double pricePerStock;
    @Column(name="Total_equity")
    Double totalEquity;
    @Column(name="Time_stamp", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public UserStockActivity(int userID, String stockSymbol, String activity, Double quantity,
                        Double pricePerStock, Double totalEquity) {
        this.userID = userID;
        this.stockSymbol = stockSymbol;
        this.activity = activity;
        this.quantity = quantity;
        this.pricePerStock = pricePerStock;
        this.totalEquity = totalEquity;
    }

}

package com.trading.service.stock.transaction.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="user_stock_activity")
public class UserActivity {
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
    @Column(name="Time_stamp")
    Date time;

    public UserActivity(int userID, String stockSymbol, String activity, Double quantity,
                        Double pricePerStock, Double totalEquity,Date time) {
        this.userID = userID;
        this.stockSymbol = stockSymbol;
        this.activity = activity;
        this.quantity = quantity;
        this.pricePerStock = pricePerStock;
        this.totalEquity = totalEquity;
        this.time=time;
    }
}

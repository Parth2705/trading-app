package com.trading.service.stocks.fetch.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stocks {
//        symbol=A, name=Agilent Technologies Inc. Common Stock, =60.43
        private String symbol;
        private String name;
        private BigDecimal lastSale;

}

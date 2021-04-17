package com.trading.service.stocks.fetch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Watching {
    private String symbol;
    private boolean watching;
}

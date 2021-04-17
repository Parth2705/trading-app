package com.trading.service.stocks.fetch.client;


import com.trading.service.stocks.fetch.model.Stocks;
import io.reactivex.Observable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class StocksApiClient {
    WebClient client ;

    @Value("${stock.api}")
    String url;

    public Observable<Stocks> getStockListing() {

        log.info("Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName());
        this.client = WebClient.builder().baseUrl(url).build();
        return Observable.fromIterable(this.client.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Stocks.class)
                .toIterable());
    }
}

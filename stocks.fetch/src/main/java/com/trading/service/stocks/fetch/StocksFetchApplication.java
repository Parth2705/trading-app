package com.trading.service.stocks.fetch;

import com.trading.service.stocks.fetch.service.impl.StockListingServiceStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class StocksFetchApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksFetchApplication.class, args);
	}
	@Autowired
    StockListingServiceStream stockListingService;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		log.info("Inside " + this.getClass() + "."
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		stockListingService.updateStocks();
	}
}

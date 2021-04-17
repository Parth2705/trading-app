package com.virtusa.training.stockmarketapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/stocks")
class StockCtrl {

    @Autowired
    lateinit var stockService: StockService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getStocks(): Mono<MutableList<Stocks>> {
        return Mono.just(stockService.getStockData())
    }
//    every 3 seconds
    @GetMapping("/feed", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getChangeFeed(): Flux<Map<String, Stocks>> {
        return stockService.getChanges()
    }
}
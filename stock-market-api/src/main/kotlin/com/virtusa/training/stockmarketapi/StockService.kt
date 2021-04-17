package com.virtusa.training.stockmarketapi

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxProcessor
import reactor.core.publisher.FluxSink
import reactor.core.publisher.ReplayProcessor
import sun.security.ec.point.ProjectivePoint
import java.io.File
import java.math.BigDecimal
import java.time.Duration
import java.util.stream.IntStream
import kotlin.random.Random

@Service
class StockService {

    var stocksMap: Map<String, Stocks> = mutableMapOf()
    var keys: List<String> = mutableListOf()
    val changeProcessor: FluxProcessor<Map<String, Stocks>, Map<String, Stocks>> = ReplayProcessor.create()
    val changeSink: FluxSink<Map<String, Stocks>> = changeProcessor.sink()

    init {
        val file = File("/Users/parthpatwari/trading-app/trading_app-master/stock-market-api/src/main/resources/stocks.csv")
        val readAllLines = file.readLines()
        readAllLines
                .stream()
                .filter({ item -> !item.contains("Symbol,Name,Last Sale") })
                .map { item: String ->
                    val stockData = item.split(",").toTypedArray()
                    Stocks(
                            stockData[0],
                            stockData[1],
                            BigDecimal(stockData[2].trim().replace("$", "")),
//                            BigDecimal(stockData[3].trim()),
//                            BigDecimal(stockData[4].trim().replace("%", "")),
//                            stockData[5].trim().replace("%", "").toLong(),
//                            stockData[7],
//                            stockData[8].trim().replace("%", "").toLong(),
//                            stockData[9],
//                            stockData[10]
                    )
                }.forEach {
                    stocksMap = stocksMap.plus(it.symbol to it)
                    keys = keys.plus(it.symbol)
                }


        Flux.interval(Duration.ofSeconds(3))
                .doOnEach { item ->
                    //println("Ticker " + item?.get())
                    var changes: Map<String, Stocks> = mutableMapOf()
                    IntStream

                            .range(Random.nextInt(stocksMap.size/2), Random.nextInt((stocksMap.size)/2+1, stocksMap.size) )
                            .forEach { index ->
                                val key = keys.get(index)
                                val stock = stocksMap.get(key)
                                stocksMap = stocksMap.plus(
                                        key to stock!!.change()
                                )
                                changes = changes.plus(key to stock)
                            }

                    println("Changes" + changes)
                    changeSink.next(changes)
                }.subscribe()
    }

    fun getStockData(): MutableList<Stocks> {
        return stocksMap.values.toMutableList()
    }

    fun getChanges(): Flux<Map<String, Stocks>> {
        return changeProcessor
    }
}
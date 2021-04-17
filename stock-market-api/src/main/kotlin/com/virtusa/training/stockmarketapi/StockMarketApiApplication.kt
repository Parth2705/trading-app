package com.virtusa.training.stockmarketapi

import jdk.nashorn.internal.objects.NativeRegExp
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockMarketApiApplication

fun main(args: Array<String>) {
	println(11)
	runApplication<StockMarketApiApplication>(*args)
}

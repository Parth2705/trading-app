package com.virtusa.training.stockmarketapi

import java.math.BigDecimal
import kotlin.random.Random

data class Stocks(
        val symbol: String,
        val name: String,
        var lastSale: BigDecimal,
//        var netChange: BigDecimal,
//        var percentageChange: BigDecimal,
//        val marketCap: Long,
//        val ipoYear: String,
//        val volume: Long,
//        val sector: String,
//        val industry: String
) {
    fun change(): Stocks {
        if (System.currentTimeMillis() % 2 == 1L) {
            this.lastSale = this.lastSale.plus(BigDecimal.valueOf(Random.nextDouble(5.0)))
                    .setScale(2, 0)
//            this.netChange = BigDecimal.valueOf(Random.nextDouble(1.0)).setScale(2, 0)
//            this.percentageChange = BigDecimal.valueOf(Random.nextDouble(1.0)).setScale(2, 0)
        } else {
            this.lastSale = this.lastSale.minus(BigDecimal.valueOf(Random.nextDouble(5.0)))
                    .setScale(2, 0)
//            this.netChange = BigDecimal.valueOf(Random.nextDouble(1.0)).setScale(2, 0).negate()
//            this.percentageChange = BigDecimal.valueOf(Random.nextDouble(1.0)).setScale(2, 0).negate()
        }

        return this
    }
}
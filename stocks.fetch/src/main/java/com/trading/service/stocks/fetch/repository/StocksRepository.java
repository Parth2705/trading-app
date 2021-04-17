package com.trading.service.stocks.fetch.repository;

import com.trading.service.stocks.fetch.model.entity.StocksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends JpaRepository<StocksEntity, Integer> {

    @Query("SELECT s FROM StocksEntity s WHERE s.stockSymbol =:symbol")
    StocksEntity findBySymbol(@Param("symbol") String symbol);
}

package com.trading.service.stocks.fetch.repository;


import com.trading.service.stocks.fetch.model.entity.StocksPortfolioEntity;
import com.trading.service.stocks.fetch.model.entity.StocksWatchListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksWatchListRepository extends JpaRepository<StocksWatchListEntity, Integer> {

    @Query("SELECT s FROM StocksWatchListEntity s WHERE s.userId =:user_id")
    List<StocksWatchListEntity> findByUserId(@Param("user_id") int user_id);

    @Query("SELECT s FROM StocksWatchListEntity s WHERE s.userId =:user_id and s.stockSymbol =:symbol")
    StocksWatchListEntity findByUserIdAndSymbol(@Param("user_id") int user_id, @Param("symbol") String symbol);
}

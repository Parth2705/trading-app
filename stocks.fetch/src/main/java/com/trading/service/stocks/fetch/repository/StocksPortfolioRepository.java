package com.trading.service.stocks.fetch.repository;

import com.trading.service.stocks.fetch.model.entity.StocksPortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksPortfolioRepository extends JpaRepository<StocksPortfolioEntity, Integer> {

    @Query("SELECT s FROM StocksPortfolioEntity s WHERE s.userId =:userId")
    List<StocksPortfolioEntity> findByUserId(@Param("userId") int userId);

//    SELECT * FROM trading.stock_portfolio s WHERE s.userId =1 and s.stock_symbol = 'AAL'
    @Query("SELECT s FROM StocksPortfolioEntity s WHERE s.userId =:userId and s.stockSymbol =:symbol")
    StocksPortfolioEntity findByUserIdAndSymbol(@Param("userId") int userId, @Param("symbol") String symbol);
}

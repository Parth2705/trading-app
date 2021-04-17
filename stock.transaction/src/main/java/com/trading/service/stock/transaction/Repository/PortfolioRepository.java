package com.trading.service.stock.transaction.repository;

import com.trading.service.stock.transaction.model.entity.PortfolioData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioData,Integer> {

        Optional<PortfolioData> findByUserIdAndStockSymbol( int userId,String stockSymbol);
}

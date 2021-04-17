package com.trading.service.stock.transaction.service;

import com.trading.service.stock.transaction.model.Stock;
import com.trading.service.stock.transaction.model.entity.PortfolioData;
import com.trading.service.stock.transaction.repository.PortfolioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class PortfolioService {
    @Autowired
    PortfolioRepository portfolioRepository;


    public PortfolioData addStock(Stock stock, int userId) throws EntityNotFoundException{
        log.info("Stock Transaction service: inside addStock -PortfolioService");
        Optional<PortfolioData> optional=  portfolioRepository.findByUserIdAndStockSymbol(userId, stock.getSymbol());
        PortfolioData portfolioData;
        if(optional.isPresent())
        {
            portfolioData= optional.get();
            Double totalEquity=portfolioData.getTotalEquity()+(stock.getQuantity()* stock.getPricePerStock());
            Double numberOfStocks=portfolioData.getQuantity()+ stock.getQuantity();
            double avg=totalEquity/numberOfStocks;
            portfolioData.setAveragePrice(avg);
            portfolioData.setQuantity(numberOfStocks);
            portfolioData.setTotalEquity(totalEquity);
            portfolioRepository.save(portfolioData);
          return portfolioData;
        }

       portfolioData=new PortfolioData(userId, stock.getSymbol(),
               stock.getQuantity(), stock.getPricePerStock(),
               (stock.getQuantity())* stock.getPricePerStock());
        portfolioRepository.save(portfolioData);
        return portfolioData;

    }

    public PortfolioData sellStock(Stock stock, int userId) throws EntityNotFoundException{
        log.info("Stock Transaction service: inside sellStock -PortfolioService");
        PortfolioData portfolioData=  portfolioRepository.findByUserIdAndStockSymbol(userId, stock.getSymbol())
                                                        .orElseThrow(()->new EntityNotFoundException());
        if (Double.compare(portfolioData.getQuantity(),stock.getQuantity())==0)
        {

            portfolioRepository.deleteById(portfolioData.getId());
            return new PortfolioData();
        }

        portfolioData.setQuantity(portfolioData.getQuantity()-stock.getQuantity());
        portfolioData.setTotalEquity(portfolioData.getQuantity()* portfolioData.getAveragePrice());

        portfolioRepository.save(portfolioData);
        return portfolioData;
    }
}

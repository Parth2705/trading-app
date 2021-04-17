package com.trading.service.stock.transaction.controller.usercontroller;

import com.trading.service.stock.transaction.model.Activity;
import com.trading.service.stock.transaction.model.entity.UserActivity;
import com.trading.service.stock.transaction.model.Stock;
import com.trading.service.stock.transaction.model.entity.PortfolioData;
import com.trading.service.stock.transaction.service.PortfolioService;
import com.trading.service.stock.transaction.service.UserActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserStockTransactionController {

    @Autowired
    PortfolioService portfolioService;
    @Autowired
    UserActivityService userActivityService;

    @PostMapping("/stock")
    public ResponseEntity<PortfolioData> buyStock(@RequestBody Stock stock, HttpSession session) throws EntityNotFoundException
    {
        log.info("Stock Transaction service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );

        final int userId=(int) session.getAttribute("userId");
        portfolioService.addStock(stock,userId);
        userActivityService.addActivity(Activity.BUY,userId,stock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/stock")
    public ResponseEntity<PortfolioData> sellStock(@RequestBody Stock stock, HttpSession session) throws EntityNotFoundException
    {
        log.info("Stock Transaction service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );

        final int userId=(int) session.getAttribute("userId");
        portfolioService.sellStock(stock,userId);
        userActivityService.addActivity(Activity.SELL,userId,stock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/Stock-activity")
    public ResponseEntity<List<UserActivity>> getUserActivity(HttpSession session)
    {
        log.info("Stock Transaction service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );

        final int userId=(int) session.getAttribute("userId");
        return new ResponseEntity<>(userActivityService.getUserActivity(userId),HttpStatus.OK);
    }

}

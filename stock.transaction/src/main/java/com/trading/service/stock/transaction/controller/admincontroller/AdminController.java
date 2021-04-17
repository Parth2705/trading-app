package com.trading.service.stock.transaction.controller.admincontroller;

import com.trading.service.stock.transaction.model.entity.UserActivity;
import com.trading.service.stock.transaction.service.UserActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserActivityService userActivityService;

    @GetMapping("/Stock-activity")
    public ResponseEntity<List<UserActivity>> getAllUserActivity(HttpSession session)
    {

        log.info("Stock Transaction service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );
        return new ResponseEntity<>(userActivityService.getAllUserActivity(), HttpStatus.OK);

    }
}

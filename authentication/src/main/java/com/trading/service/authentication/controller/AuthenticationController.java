package com.trading.service.authentication.controller;


import com.trading.service.authentication.Model.AuthenticationRequest;
import com.trading.service.authentication.Service.Authenticate;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    Authenticate authenticate;


    @PostMapping(value = "/login")
    public ResponseEntity<String> userLogin(@RequestBody AuthenticationRequest authenticationRequest, HttpSession session)  {

        log.info("From Authentication service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );
        if(authenticate.check(authenticationRequest,session))
        {
           session.setAttribute("userName",authenticationRequest.getUsername());

           log.info("From Authentication service: Login successful for the userId: "+session.getAttribute("username"));
           return new ResponseEntity<>("logged in Successfully", HttpStatus.OK);
        }
        log.warn("From Authentication service: Login unsuccessful for sessionID "+session.getId());
        return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> userLogout(HttpSession session) throws NullPointerException
    {
        log.info("From Authentication service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );
       session.invalidate();
       return new ResponseEntity<>( HttpStatus.OK);

    }

}

package com.trading.service.authentication.Service;


import com.trading.service.authentication.Model.AuthenticationRequest;
import com.trading.service.authentication.Model.entity.UserCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class Authenticate {
    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private BcryptPasswordChecker bcryptpasswordChecker;

    public boolean check(AuthenticationRequest authenticationRequest, HttpSession session) throws EntityNotFoundException
    {

        UserCredentials userCredentials= userCredentialService.loadByUsername(authenticationRequest.getUsername());
        session.setAttribute("userId",userCredentials.getUserID());
        session.setAttribute("role",userCredentials.getRole());
        if(userCredentials!=null && bcryptpasswordChecker.check(authenticationRequest.getPassword(),userCredentials.getPassword()))
        {
            log.info("From Authentication service: credentials are Valid");
            return true;
        }
        log.warn("From Authentication service: credentials are Invalid");
        return false;
    }


}

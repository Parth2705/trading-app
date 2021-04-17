package com.trading.service.authentication.Service;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BcryptPasswordChecker {

    public boolean check(String password, String  dbPassword)
    {
        return BCrypt.checkpw(password,dbPassword);
    }
}

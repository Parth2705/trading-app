package com.trading.service.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptor {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encrypt(String password)
    {
        return passwordEncoder.encode(password);
    }
}

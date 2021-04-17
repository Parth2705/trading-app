package com.trading.service.user.service;

import com.trading.service.user.model.entity.UserCredentials;
import com.trading.service.user.repository.UserCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Service
public class UserCredentialService {
    @Autowired
    UserCredentialsRepository userCredentialsRepository;

    @Retryable(value = { SQLException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void saveUserCredentials(UserCredentials userCredentials) throws Exception
    {
        log.info("User Service: Inside saveUserCredentials- UserCredentialService layer");
        userCredentialsRepository.save(userCredentials);
    }

    @Retryable(value = { SQLException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public UserCredentials getCredentialsByUserName(String userName) throws Exception
    {
        log.info("User Service: Inside getCredentialsByUserName- UserCredentialService layer");
       return userCredentialsRepository.findByUserName(userName);
    }
}

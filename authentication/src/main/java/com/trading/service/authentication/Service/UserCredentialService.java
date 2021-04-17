package com.trading.service.authentication.Service;


import com.trading.service.authentication.Model.entity.UserCredentials;
import com.trading.service.authentication.Repository.UserCredentialsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
public class UserCredentialService {

    @Autowired
    UserCredentialsRepo userCredentialsRepo;

    public UserCredentials loadByUsername(String name) throws EntityNotFoundException
    {
        log.info("From Authentication service: Inside loadByUsername-service layer");
        return userCredentialsRepo.getByUserName(name);
    }


}
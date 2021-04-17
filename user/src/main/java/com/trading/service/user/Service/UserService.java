package com.trading.service.user.service;

import com.trading.service.user.model.entity.UserCredentials;
import com.trading.service.user.model.entity.UserDetails;
import com.trading.service.user.model.UserDetailsForm;
import com.trading.service.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserCredentialService userCredentialService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Retryable(value = { SQLException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public UserDetails getUserById(int userId) throws EntityNotFoundException
    {
        log.info("User Service: Inside saveUserCredentials- UserService layer");
        return  userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
    }

    @Retryable(value = { SQLException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public UserDetails getUserByUserName(String userName) throws EntityNotFoundException
    {
        log.info("User Service: Inside getUserByUserName- UserService layer");
        return  userRepository.findUserByUserName(userName).orElseThrow(() -> new EntityNotFoundException());
    }

    @Retryable(value = { SQLException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void saveUser(UserDetails userDetails)
    {
        log.info("User Service: Inside saveUser- UserService layer");
        userRepository.save(userDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addNewUser(UserDetailsForm userDetailsForm) throws Exception {
        log.info("User Service: Inside addNewUser- UserService layer");
        UserDetails userDetails=new UserDetails(userDetailsForm.getUserName(),userDetailsForm.getFirstName(),
                userDetailsForm.getLastName(),userDetailsForm.getEmail(),userDetailsForm.getPhoneNumber(),userDetailsForm.getSsn());
        userRepository.save(userDetails);
        UserCredentials userCredentials=new UserCredentials(userDetailsForm.getUserName(),passwordEncoder.encode(userDetailsForm.getPassword()),userDetails.getId(),"USER");

        userCredentialService.saveUserCredentials(userCredentials);
    }

    public boolean isUserPresent(String userName) {
        return  userRepository.findUserByUserName(userName).isPresent();
    }
}

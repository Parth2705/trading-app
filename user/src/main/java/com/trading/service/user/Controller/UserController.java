package com.trading.service.user.Controller;


import com.trading.service.user.model.entity.UserCredentials;
import com.trading.service.user.model.entity.UserDetails;
import com.trading.service.user.model.UpdateUser;
import com.trading.service.user.model.UserDetailsForm;
import com.trading.service.user.service.PasswordEncryptor;
import com.trading.service.user.service.UserCredentialService;
import com.trading.service.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserCredentialService userCredentialService;

    @Autowired
    PasswordEncryptor passwordEncryptor;

    @GetMapping("/user-details")
    public ResponseEntity<UserDetails> getUser(HttpSession session) throws EntityNotFoundException {
        log.info("User Service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );
        String userName= (String) session.getAttribute("userName");
        UserDetails userDetails =userService.getUserByUserName(userName);
        userDetails.setSsn( userDetails.getSsn().substring(userDetails.getSsn().length() - 4));
        return new ResponseEntity<>(userDetails, HttpStatus.OK);

    }

    @PutMapping("/update-details")
    public ResponseEntity<UserDetails> updateUser(@RequestBody UpdateUser updateUser, HttpSession session)throws Exception
    {

        log.info("User Service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );
        final String userName= (String) session.getAttribute("userName");
        UserDetails userDetails=userService.getUserByUserName(userName);
        userDetails.setEmail(updateUser.getEmail());
        userDetails.setFirstName(updateUser.getFirstName());
        userDetails.setLastName(updateUser.getLastName());
        userDetails.setPhoneNumber(updateUser.getPhoneNumber());
        userService.saveUser(userDetails);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/new-user")
    public ResponseEntity<UserDetailsForm> addNewUser(@RequestBody UserDetailsForm userDetailsForm)throws Exception
    {

        log.info("User Service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName()  );

        if(userService.isUserPresent(userDetailsForm.getUserName()))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.addNewUser(userDetailsForm);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updateUserPassword(@RequestHeader String oldPassword,
                                                     @RequestHeader String newPassword,HttpSession session) throws Exception
    {
        log.info("User Service: Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+session.getAttribute("username") );
        final String userName= (String) session.getAttribute("userName");
        UserCredentials userCredentials= userCredentialService.getCredentialsByUserName(userName);
        if(BCrypt.checkpw(oldPassword,userCredentials.getPassword()))
        {
           userCredentials.setPassword(passwordEncryptor.encrypt(newPassword));
           userCredentialService.saveUserCredentials(userCredentials);
           return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        log.warn("User Service: update password failed " + " for userId: "+session.getAttribute("username"));
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
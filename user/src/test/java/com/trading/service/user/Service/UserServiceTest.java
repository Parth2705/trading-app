package com.trading.service.user.Service;


import com.trading.service.user.model.UserDetailsForm;
import com.trading.service.user.model.entity.UserCredentials;
import com.trading.service.user.model.entity.UserDetails;
import com.trading.service.user.repository.UserRepository;
import com.trading.service.user.service.UserCredentialService;
import com.trading.service.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    UserCredentialService userCredentialService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    UserDetails userDetails;
    UserCredentials userCredentials;
    UserDetailsForm userDetailsForm;

    @BeforeEach
    void setUp() {

        userCredentials=new UserCredentials("asd","name",1,"USER");
        userDetails=new UserDetails(1,"asd","asd","asd","asd","asd","asd");
        userDetailsForm=new UserDetailsForm("asd","asd","asd","asd","asd","asd","asd");
    }
    @Test
    public void getUserByIdTest() throws Exception {

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(userDetails));
        assertEquals(userDetails,userService.getUserById(1));
    }
    @Test
    public void getUserByUserNameTest() throws Exception {

        when(userRepository.findUserByUserName("test")).thenReturn(Optional.ofNullable(userDetails));
        assertEquals(userDetails,userService.getUserByUserName("test"));
    }



    @Test
    public void isUserPresentTest() throws Exception {
        when(userRepository.findUserByUserName("test")).thenReturn(Optional.ofNullable(userDetails));
        assertTrue(userService.isUserPresent("test"));
    }




}

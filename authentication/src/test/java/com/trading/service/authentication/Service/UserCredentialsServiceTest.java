package com.trading.service.authentication.Service;



import com.trading.service.authentication.model.AuthenticationRequest;
import com.trading.service.authentication.model.entity.UserCredentials;
import com.trading.service.authentication.repository.UserCredentialsRepo;
import com.trading.service.authentication.service.UserCredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserCredentialsServiceTest {

    @Autowired
    UserCredentialService userCredentialService;

    @MockBean
    UserCredentialsRepo userCredentialsRepo;


    UserCredentials UserCredentials;

    @BeforeEach
    void setUp() {

        UserCredentials=new UserCredentials(1,2,"test","pass","USER");
    }

    @Test
    public void userDetailsServiceSuccess()
    {
        Mockito.doReturn(UserCredentials).when(userCredentialsRepo).getByUserName("test");
        assertEquals(UserCredentials, userCredentialService.loadByUsername("test"));
    }
}

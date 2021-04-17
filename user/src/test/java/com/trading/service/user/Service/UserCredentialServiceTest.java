package com.trading.service.user.Service;


import com.trading.service.user.model.entity.UserCredentials;
import com.trading.service.user.repository.UserCredentialsRepository;
import com.trading.service.user.service.UserCredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserCredentialServiceTest {
    @Autowired
    UserCredentialService userCredentialService;
    @MockBean
    UserCredentialsRepository userCredentialsRepository;

    UserCredentials userCredentials;


    @BeforeEach
    void setUp() {

        userCredentials = new UserCredentials("test","name",1,"USER");
    }

    @Test
    public void getCredentialsByUserNameTest() throws Exception {
        when(userCredentialsRepository.findByUserName("test")).thenReturn(userCredentials);
        assertEquals(userCredentials,userCredentialService.getCredentialsByUserName("test"));
    }


}

package com.trading.service.authentication.Service;




import com.trading.service.authentication.model.AuthenticationRequest;
import com.trading.service.authentication.model.entity.UserCredentials;
import com.trading.service.authentication.service.Authenticate;
import com.trading.service.authentication.service.BcryptPasswordChecker;
import com.trading.service.authentication.service.UserCredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AuthenticateTest {

    @Autowired
    Authenticate authenticate;

    @MockBean
    private UserCredentialService userCredentialService;

    @MockBean
    private BcryptPasswordChecker bcryptpasswordChecker;

    AuthenticationRequest authenticationRequest;
    UserCredentials userCredentials;

    @BeforeEach
    void setUp() {
        authenticationRequest=new AuthenticationRequest("test","pass");
        userCredentials=new UserCredentials(1,1,"test","$2a$10$eBAo003H6set1cqyJ5HawuHIED61HQmRs2CakTuQlCK1fCph2Zi/6","USER");
    }

    @Test
    public void authenticationServiceSuccess()
    {
        when(userCredentialService.loadByUsername("test")).thenReturn(userCredentials);
        MockHttpSession session = new MockHttpSession();
        when(bcryptpasswordChecker.check("pass","$2a$10$eBAo003H6set1cqyJ5HawuHIED61HQmRs2CakTuQlCK1fCph2Zi/6")).thenReturn(true);
        assertEquals(true,authenticate.check(authenticationRequest,session));
    }
}

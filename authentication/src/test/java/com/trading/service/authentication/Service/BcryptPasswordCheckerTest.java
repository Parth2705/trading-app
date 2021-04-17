package com.trading.service.authentication.Service;


import com.trading.service.authentication.service.BcryptPasswordChecker;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BcryptPasswordCheckerTest {
    @Autowired
    BcryptPasswordChecker bcryptPasswordChecker;

    @Test
    public void bcryptPasswordCheckerServiceSuccess()
    {

        assertEquals(true,bcryptPasswordChecker.check("pass","$2a$10$eBAo003H6set1cqyJ5HawuHIED61HQmRs2CakTuQlCK1fCph2Zi/6"));

    }
}

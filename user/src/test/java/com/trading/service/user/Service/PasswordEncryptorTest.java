package com.trading.service.user.Service;


import com.trading.service.user.service.PasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PasswordEncryptorTest {
   @Autowired
    PasswordEncryptor passwordEncryptor;

    @Test
    public void encryptTest()
    {
        assertTrue(BCrypt.checkpw("pass",passwordEncryptor.encrypt("pass")));

    }
}

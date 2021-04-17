package com.trading.service.user.Exception;


import com.trading.service.user.Controller.UserController;
import com.trading.service.user.service.PasswordEncryptor;
import com.trading.service.user.service.UserCredentialService;
import com.trading.service.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class GlobalExceptionHandlerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;
    @MockBean
    UserCredentialService userCredentialService;
    @MockBean
    PasswordEncryptor passwordEncryptor;


    @Test
    public void ullPointerExceptionTest() throws Exception {

        doThrow(new NullPointerException()).
                when(userCredentialService).getCredentialsByUserName(any());
        mockMvc.perform(post("/user/update-password")
                .header("oldPassword", "test")
                .header("newPassword", "test")
                .sessionAttr("userName", "test"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void EntityNotFoundExceptionTest() throws Exception {

        doThrow(new EntityNotFoundException()).
                when(userCredentialService).getCredentialsByUserName(any());
        mockMvc.perform(post("/user/update-password")
                .header("oldPassword", "test")
                .header("newPassword", "test")
                .sessionAttr("userName", "test"))
                .andExpect(status().isNotFound());


    }
}
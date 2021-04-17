package com.trading.service.authentication.filter;

import com.trading.service.authentication.controller.AuthenticationController;
import com.trading.service.authentication.model.AuthenticationRequest;
import com.trading.service.authentication.service.Authenticate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class SessionValidationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private Authenticate auth;


    @Test
    public void logoutSuccess() throws Exception {

        mockMvc.perform( post("/logout").sessionAttr("userName","value"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void logoutFail() throws Exception {

        mockMvc.perform( post("/logout"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

}

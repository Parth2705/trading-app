package com.trading.service.authentication.controller;


import com.trading.service.authentication.model.AuthenticationRequest;


import com.trading.service.authentication.service.Authenticate;
import org.junit.jupiter.api.DisplayName;
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

@DisplayName("Controller layer Testing")
@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private Authenticate auth;

   @Test
   public void loginSuccess() throws Exception {

       MockHttpSession session = new MockHttpSession();
       when(auth.check(any(AuthenticationRequest.class),any())).thenReturn(true);

       mockMvc.perform( post("/login")
               .content("{\"username\":\"usr\",\"password\":\"pass\"}")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();
   }
    @Test
    public void loginFail() throws Exception {
        MockHttpSession session = new MockHttpSession();
        when(auth.check(any(AuthenticationRequest.class),any())).thenReturn(false);

        mockMvc.perform( post("/login")
                .content("{\"username\":\"usr\",\"password\":\"pass\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()).andReturn();
    }
    @Test
    public void logoutSuccess() throws Exception {

        mockMvc.perform( post("/logout").sessionAttr("userName","value"))
                .andExpect(status().isOk()).andReturn();
    }



}

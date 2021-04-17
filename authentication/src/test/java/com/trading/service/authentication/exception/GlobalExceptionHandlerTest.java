package com.trading.service.authentication.exception;

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

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class GlobalExceptionHandlerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private Authenticate auth;
    @Test
    public void nullPointerExceptionTest() throws Exception {

        MockHttpSession session = new MockHttpSession();
        when(auth.check(any(AuthenticationRequest.class),any())).thenThrow(new NullPointerException());

        mockMvc.perform( post("/login")
                .content("{\"username\":\"usr\",\"password\":\"pass\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void entityNotFoundExceptionTest() throws Exception {

        MockHttpSession session = new MockHttpSession();
        when(auth.check(any(AuthenticationRequest.class),any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform( post("/login")
                .content("{\"username\":\"usr\",\"password\":\"pass\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
    }
}

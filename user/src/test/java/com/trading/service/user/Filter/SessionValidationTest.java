package com.trading.service.user.Filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.service.user.Controller.UserController;
import com.trading.service.user.model.UpdateUser;
import com.trading.service.user.model.UserDetailsForm;
import com.trading.service.user.model.entity.UserCredentials;
import com.trading.service.user.model.entity.UserDetails;
import com.trading.service.user.service.PasswordEncryptor;
import com.trading.service.user.service.UserCredentialService;
import com.trading.service.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class SessionValidationTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserCredentialService userCredentialService;

    @MockBean
    PasswordEncryptor passwordEncryptor;

    UserDetails userDetails;
    UserCredentials userCredentials;
    UserDetailsForm userDetailsForm;
    UpdateUser updateUser;
    @BeforeEach
    void setUp() {

        updateUser=new UpdateUser("test","test","test","test");
        userCredentials=new UserCredentials("test",passwordEncryptor.encrypt("test"),1,"USER");
        userDetails=new UserDetails(1,"asd","asd","asd","asd","asd","asdasdasd");
        userDetailsForm=new UserDetailsForm("asd","asd","asd","asd","asd","asdasdasd","asd");
    }

    @Test
    public void getUserTest() throws Exception {
        when(userService.getUserByUserName(anyString())).thenReturn(userDetails);
        mockMvc.perform( get("/user/user-details"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    public void updateUserTest() throws Exception {
        when(userService.getUserByUserName("test")).thenReturn(userDetails);
        doNothing().when(userService).saveUser(any(UserDetails.class));
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(updateUser);
        mockMvc.perform( put("/user/update-details")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()).andReturn();
    }


    @Test
    public void updateUserPasswordTest() throws Exception {

        userCredentials=new UserCredentials("test","$2y$12$R9hL0E2C3Y1cp8umkns61.8sHKbIVLnzZ2NY30xLNM2kmfH314j/. ",1,"USER");
        when(userCredentialService.getCredentialsByUserName("test")).thenReturn(userCredentials);
        doNothing().when(userCredentialService).saveUserCredentials(any(UserCredentials.class));

        mockMvc.perform( post("/user/update-password")
                .header("oldPassword","test")
                .header("newPassword","test"))
                .andExpect(status().isUnauthorized()).andReturn();
    }
}
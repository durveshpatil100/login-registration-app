package com.app;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.model.User;
import com.app.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationTest {

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSuccessfulRegistration() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "testuser")
                .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        User user = userRepository.findByEmail("testuser");
        Assertions.assertNotNull(user);
       
    }

    @Test
    public void testInvalidRegistration() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "testuser")
                .param("password", "pass")) // Invalid password
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().hasErrors());

        User user = userRepository.findByEmail("testuser");
        Assertions.assertNull(user);
    }
	
}

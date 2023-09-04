package com.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSuccessfulLogin() {
        ResponseEntity<String> response = restTemplate.postForEntity("/login",
                new LinkedMultiValueMap<String, String>() {{
                    add("username", "your_username");
                    add("password", "your_password");
                }},
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Welcome to the Dashboard");
    }

    @Test
    public void testInvalidLogin() {
        ResponseEntity<String> response = restTemplate.postForEntity("/login",
                new LinkedMultiValueMap<String, String>() {{
                    add("username", "invalid_username");
                    add("password", "invalid_password");
                }},
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Invalid username or password");
    }
}

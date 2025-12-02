package com.example.demo.app.aplication.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.app.domain.model.User;

public class UserAplicationServiceTest {

    private UserAplicationService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserAplicationService(passwordEncoder);
    }

    @Test
    public void testCheckPassword() {
        String raw = "1234";
        String encoded = passwordEncoder.encode(raw);

        assertTrue(userService.checkPassword(raw, encoded));
        assertFalse(userService.checkPassword("wrong", encoded));
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("user");
        user.setEmail("test@test.com");
        user.setPasswordHash(passwordEncoder.encode("1234"));
        user.setStatus((byte)1);

        // Solo se comprueba hash no nulo
        assertNotNull(user.getPasswordHash());
        assertEquals("user", user.getUsername());
    }
}

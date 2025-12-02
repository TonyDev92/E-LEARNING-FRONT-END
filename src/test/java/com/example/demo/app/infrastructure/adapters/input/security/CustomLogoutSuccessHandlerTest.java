package com.example.demo.app.infrastructure.adapters.input.security;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.example.demo.services.LogoutService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLogoutSuccessHandlerTest {

    @Mock
    private LogoutService logoutService;

    @InjectMocks
    private CustomLogoutSuccessHandler logoutHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private WebAuthenticationDetails authDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOnLogoutSuccess() throws IOException {
        System.out.println("=== testOnLogoutSuccess ===");

        when(authentication.getDetails()).thenReturn(authDetails);
        when(authDetails.getSessionId()).thenReturn("123456");
        when(logoutService.logout("123456")).thenReturn(1);

        logoutHandler.onLogoutSuccess(request, response, authentication);

        verify(logoutService, times(1)).logout("123456");
        verify(response, times(1)).sendRedirect("/login?logout=true");

        System.out.println("Logout executed: tokens deleted and redirect performed");
    }

    @Test
    public void testOnLogoutSuccessWithNullAuthentication() throws IOException {
        System.out.println("=== testOnLogoutSuccessWithNullAuthentication ===");

        logoutHandler.onLogoutSuccess(request, response, null);

        verify(logoutService, times(0)).logout(anyString());
        verify(response, times(1)).sendRedirect("/login?logout=true");

        System.out.println("Logout executed with null authentication: only redirect performed");
    }
}


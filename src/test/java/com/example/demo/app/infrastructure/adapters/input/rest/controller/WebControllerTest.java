package com.example.demo.app.infrastructure.adapters.input.rest.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.app.aplication.port.in.UserUseCasePort;

public class WebControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserUseCasePort userUseCase;

    @InjectMocks
    private WebController webController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(webController).build();
    }

    @Test
    public void testRootRedirect() throws Exception {
        System.out.println("=== testRootRedirect ===");
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andDo(result -> System.out.println("Redirect URL: " + result.getResponse().getRedirectedUrl()));
    }

    @Test
    public void testLoginWithoutParams() throws Exception {
        System.out.println("=== testLoginWithoutParams ===");
        Authentication auth = mock(AnonymousAuthenticationToken.class);
        when(userUseCase.login(null, null, null, auth)).thenReturn("login");

        ModelAndView model = webController.login(null, null, null, auth);

        System.out.println("View name: " + model.getViewName());
        System.out.println("Model: " + model.getModel());

        verify(userUseCase, times(1)).login(null, null, null, auth);
    }

    @Test
    public void testLoginWithError() throws Exception {
        System.out.println("=== testLoginWithError ===");
        Authentication auth = mock(AnonymousAuthenticationToken.class);
        when(userUseCase.login("someError", null, null, auth)).thenReturn("login");

        ModelAndView model = webController.login("someError", null, null, auth);

        System.out.println("View name: " + model.getViewName());
        System.out.println("Model: " + model.getModel());

        assert model.getModel().containsKey("error");
        verify(userUseCase, times(1)).login("someError", null, null, auth);
    }

    @Test
    public void testHome() throws Exception {
        System.out.println("=== testHome ===");
        mockMvc.perform(get("/private/home"))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println("View for /private/home: private/home"));
    }
}


package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.example.demo.services.LogoutServiceImpl;

import jakarta.servlet.http.HttpSession;



@Controller
public class WebController {
	
	@Autowired
    private LogoutServiceImpl logoutService;

    

    // Redirige "/" a "/login"
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    // Página de login
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              @RequestParam(value = "csrfToken", required = false) String csrfToken,
                              Authentication auth) {

        // Solo redirige a /private/home si ya está autenticado
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return new ModelAndView("redirect:/private/home");
        }

        ModelAndView model = new ModelAndView("login"); // carga login.jsp

        if (error != null) {
            model.addObject("error", "Usuario o contraseña incorrectos!");
        }
        if (logout != null) {
            model.addObject("msg", "Has cerrado sesión correctamente.");
        }

        return model;
    }

    // Página privada
    @GetMapping("/private/home")
    public String home() {
        return "private/home"; // carga private/home.jsp
    }

    // ===============================================
    // LOGOUT
    // ===============================================
//   EL CONTROLADOR NO ES NECESARIO

//    @GetMapping("/auth")
//    public String logout(String session) {
//        logoutService.logout(session);
//        return "redirect:/login?logout=true";
//    }
    
    
//    @GetMapping("/auth")
//    public String logout(HttpSession httpSession) {
//
//        String sessionId = httpSession.getId(); // ← ESTA es la sesión real del usuario
//        
//        logoutService.logout(sessionId);
//
//        return "redirect:/login?logout=true";
//    }

}


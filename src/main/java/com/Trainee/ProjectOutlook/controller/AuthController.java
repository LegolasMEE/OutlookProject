package com.Trainee.ProjectOutlook.controller;

import com.Trainee.ProjectOutlook.model.AuthRequest;
import com.Trainee.ProjectOutlook.service.CustomUserDetailsService;
import com.Trainee.ProjectOutlook.model.JwtResponse;
import com.Trainee.ProjectOutlook.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Аутентификация пользователя с использованием менеджера аутентификации
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username or password", e);
        }
        // Получение данных пользователя
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        // Генерация JWT токена на основе данных пользователя
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}

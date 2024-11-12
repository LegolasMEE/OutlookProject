package com.Trainee.ProjectOutlook.controller;

import com.Trainee.ProjectOutlook.dto.request.AuthRequest;
import com.Trainee.ProjectOutlook.rateLimiter.RateLimit;
import com.Trainee.ProjectOutlook.service.AuthService;
import com.Trainee.ProjectOutlook.dto.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/authenticate")
    @RateLimit(capacity = 6, refillTokens = 2, refillPeriod = 60)
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.createAuthToken(authRequest));
    }
}

package com.Trainee.ProjectOutlook.service;

import com.Trainee.ProjectOutlook.dto.request.AuthRequest;
import com.Trainee.ProjectOutlook.dto.response.JwtResponse;
import com.Trainee.ProjectOutlook.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService userDetailsService;
    @Transactional
    public JwtResponse createAuthToken(AuthRequest authRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new JwtResponse(jwt);
    }
}

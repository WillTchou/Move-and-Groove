package com.portfolio.moveandgroove.auth;

import com.portfolio.moveandgroove.config.JwtService;
import com.portfolio.moveandgroove.user.model.Role;
import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(final RegisterRequest registerRequest) {
        final User user = buildUserWithRegisterRequest(registerRequest);
        userService.createUser(user);
        final String jwtToken = jwtService.generateToken(user);
        return buildAuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest) {
        final String requestUsername = authenticationRequest.getUsername();
        final String requestPassword = authenticationRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestUsername,
                requestPassword)
        );
        final User user = userService.getUserByUsername(requestUsername);
        final String jwtToken = jwtService.generateToken(user);
        return buildAuthenticationResponse(jwtToken);
    }

    private User buildUserWithRegisterRequest(final RegisterRequest registerRequest) {
        return User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
    }

    private AuthenticationResponse buildAuthenticationResponse(final String jwtToken) {
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

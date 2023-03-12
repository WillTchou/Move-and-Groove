package com.portfolio.moveandgroove.auth.controller;

import com.portfolio.moveandgroove.auth.service.AuthenticationService;
import com.portfolio.moveandgroove.auth.model.AuthenticationRequest;
import com.portfolio.moveandgroove.auth.model.AuthenticationResponse;
import com.portfolio.moveandgroove.auth.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody final RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody final AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}

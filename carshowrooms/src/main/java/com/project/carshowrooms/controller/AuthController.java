package com.project.carshowrooms.controller;


import com.project.carshowrooms.dto.JwtResponseDTO;
import com.project.carshowrooms.dto.LoginRequestDTO;
import com.project.carshowrooms.dto.SignupRequestDTO;
import com.project.carshowrooms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        JwtResponseDTO jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signupRequest) {
        authService.registerUser(signupRequest);
        return ResponseEntity.ok().body("User registered successfully!");
    }
}
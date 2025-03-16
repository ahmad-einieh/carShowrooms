package com.project.carshowrooms.service;


import com.project.carshowrooms.dto.JwtResponseDTO;
import com.project.carshowrooms.dto.LoginRequestDTO;
import com.project.carshowrooms.dto.SignupRequestDTO;
import com.project.carshowrooms.exception.DuplicateResourceException;
import com.project.carshowrooms.helpers.EmailValidator;
import com.project.carshowrooms.model.User;
import com.project.carshowrooms.repository.UserRepository;
import com.project.carshowrooms.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;



    @Transactional
    public JwtResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        String username = loginRequest.getUsername();
        User user = null;
        if (EmailValidator.isValidEmail(username)){
            user = userRepository.findByEmail(loginRequest.getUsername()).orElse(null);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user == null ? username : user.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        if (user == null){
            user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        }

        return new JwtResponseDTO(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Transactional
    public void registerUser(SignupRequestDTO signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new DuplicateResourceException("Username is already taken");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new DuplicateResourceException("Email is already in use");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setFullName(signupRequest.getFullName());
        user.setRole("USER");

        userRepository.save(user);
    }
}
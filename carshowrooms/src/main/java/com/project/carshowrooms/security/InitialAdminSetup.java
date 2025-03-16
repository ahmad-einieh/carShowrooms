package com.project.carshowrooms.security;

import com.project.carshowrooms.model.User;
import com.project.carshowrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialAdminSetup implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${admin.username}")
    private String adminUsername;
    
    @Value("${admin.password}")
    private String adminPassword;
    
    @Value("${admin.email}")
    private String adminEmail;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user exists
        if (!userRepository.existsByUsername(adminUsername)) {
            // Create admin user
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setFullName("System Administrator");
            adminUser.setRole("ADMIN");
            
            userRepository.save(adminUser);
            
            System.out.println("Admin user has been created.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
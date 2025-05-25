package com.ecommerce.demo.Controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.Entity.entityUser;
import com.ecommerce.demo.Repository.repositoryUser;
import com.ecommerce.demo.Service.serviceUser;


@RestController
@RequestMapping("/api/auth")
public class userController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private serviceUser serviceUser;

    @Autowired
    private com.ecommerce.demo.Utility.JwtUtil JwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private repositoryUser repositoryUser;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody entityUser request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
        );

        entityUser entityUser = serviceUser.getUserByUsername(request.getName());
        if (entityUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        String token = JwtUtil.generateToken(entityUser.getName());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/register")
    public String register(@RequestBody entityUser request) {
        
        if(serviceUser.getUserByUsername(request.getName()) != null){
            throw new IllegalArgumentException("user already exists");
        }

        entityUser entityUser = new entityUser();

        entityUser.setEmail(request.getEmail());
        entityUser.setName(request.getName());
        entityUser.setPassword(passwordEncoder.encode(request.getPassword()));
        entityUser.setRole(request.getRole() != null ? request.getRole(): "ROLE_USER");

        repositoryUser.save(entityUser);

        return "User registered successfully";
    }
    
}

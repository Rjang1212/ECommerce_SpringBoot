package com.ecommerce.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.Config.configSecurity;
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
    private configSecurity configSecurity;

    @Autowired
    private repositoryUser repositoryUser;

    @PostMapping("/login")
    public String login(@RequestBody entityUser request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
        );

        entityUser entityUser = serviceUser.getUserByUsername(request.getName());
        return JwtUtil.generateToken(entityUser.getName());
    }

    @PostMapping("/register")
    public String register(@RequestBody entityUser request) {
        
        if(serviceUser.getUserByUsername(request.getName()) != null){
            throw new IllegalArgumentException("user already exists");
        }

        entityUser entityUser = new entityUser();

        entityUser.setEmail(request.getEmail());
        entityUser.setName(request.getName());
        entityUser.setPassword(configSecurity.passwordEncoder().encode(request.getPassword()));
        entityUser.setRole(request.getRole() != null ? request.getRole(): "ROLE_USER");

        repositoryUser.save(entityUser);

        return "User registered successfully";
    }
    
}

package com.ecommerce.demo.Service;

import com.ecommerce.demo.Entity.entityUser;
import com.ecommerce.demo.Repository.repositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private repositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        entityUser user = repositoryUser.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(user.getName(), user.getPassword(), Collections.emptyList());
    }
}
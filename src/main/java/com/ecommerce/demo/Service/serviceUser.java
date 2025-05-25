package com.ecommerce.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.Entity.entityUser;
import com.ecommerce.demo.Repository.repositoryUser;

@Service
public class serviceUser {
    
    @Autowired
    private repositoryUser repositoryUser;

    public entityUser getUserByUsername(String Username) throws UsernameNotFoundException {
        entityUser entityUser = repositoryUser.findByName(Username);

        if(entityUser == null) {
            throw new UsernameNotFoundException(Username);
        }

        return entityUser;
    }
}

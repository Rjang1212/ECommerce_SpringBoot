package com.ecommerce.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.Entity.entityUser;

public interface repositoryUser  extends JpaRepository<entityUser, Long> {
    entityUser findByName(String name);
}

package com.ecommerce.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.Entity.entityProduct;

public interface repositoryProduct extends JpaRepository<entityProduct, Long> {
    entityProduct findByName(String Name);
}

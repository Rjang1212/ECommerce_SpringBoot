package com.ecommerce.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.Entity.entityCart;

public interface repositoryCart extends JpaRepository<entityCart, Long> {
    entityCart findById(long userId);
}

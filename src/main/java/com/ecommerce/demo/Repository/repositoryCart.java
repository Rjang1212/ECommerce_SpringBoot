package com.ecommerce.demo.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.Entity.entityCart;

public interface repositoryCart extends JpaRepository<entityCart, Long> {
    List<entityCart> findByUserId(Long userId);
}

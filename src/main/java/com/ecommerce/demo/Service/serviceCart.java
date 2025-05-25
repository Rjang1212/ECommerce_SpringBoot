package com.ecommerce.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.Entity.entityCart;
import com.ecommerce.demo.Entity.entityProduct;
import com.ecommerce.demo.Entity.entityUser;
import com.ecommerce.demo.Repository.repositoryCart;
import com.ecommerce.demo.Repository.repositoryProduct;
import com.ecommerce.demo.Repository.repositoryUser;

import jakarta.transaction.Transactional;

@Service
public class serviceCart {
    
    @Autowired
    private repositoryCart repositoryCart;

    @Autowired
    private repositoryProduct repositoryProduct;

    @Autowired
    private repositoryUser repositoryUser;

    public List<entityCart> getCartItems(){
        return repositoryCart.findAll();
    }
    
    @Transactional
    public void deleteCartItem(Long cartItemId){
        repositoryCart.deleteById(cartItemId);
        return;
    }

    @SuppressWarnings("deprecation")
    public void addCartItem(Long productId){
        Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if(princial instanceof entityProduct){
            username = ((entityUser) princial).getName();
        } else {
            username = princial.toString();
        }

        entityUser currentUser = repositoryUser.findByName(username);
        entityProduct entityProduct = repositoryProduct.getById(productId);

        entityCart entityCart = new entityCart();
        entityCart.setProductId(entityProduct.getId());
        entityCart.setUserId(currentUser.getId());
        entityCart.setQuantity(1);

        repositoryCart.save(entityCart);

        return;
    }
}

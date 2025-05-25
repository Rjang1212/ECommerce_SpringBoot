package com.ecommerce.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.Entity.entityCart;
import com.ecommerce.demo.Service.serviceCart;

@RestController
@RequestMapping("/api/cart")
public class cartController {

    @Autowired
    private serviceCart serviceCart;

    @GetMapping
    public List<entityCart> getCartItems(){
        return serviceCart.getCartItems();
    }

    @DeleteMapping("/{id}/delete")
    public String deleteCartItem(@PathVariable("id") Long cartItemId){
        try{
            serviceCart.deleteCartItem(cartItemId);
            return "Deleted product from cart";

        } catch(Exception e){
            return "Unable to delete" + e.toString();
        }
    }

    @PostMapping("/{id}/add")
    public String addToCart(@PathVariable("id") Long productId){
        try{
            serviceCart.addCartItem(productId);
            return "Added product to cart";

        } catch(Exception e){
            return "Unable to add" + e.toString();
        }
    }


}

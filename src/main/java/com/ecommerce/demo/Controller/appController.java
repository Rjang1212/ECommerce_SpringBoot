package com.ecommerce.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.Entity.entityProduct;
import com.ecommerce.demo.Service.serviceProduct;

@RestController
@RequestMapping("/api/products")
public class appController {
    
    @Autowired
    private serviceProduct serviceProduct;

    @GetMapping
    public List<entityProduct> getAllProducts(){
        return serviceProduct.getAllProducts();
    }

    @GetMapping("/{id}")
    public entityProduct getProductUsingID(@PathVariable long id){
        return serviceProduct.getProductById(id);
    }

}
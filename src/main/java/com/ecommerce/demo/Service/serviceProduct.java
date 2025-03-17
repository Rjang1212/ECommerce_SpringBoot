package com.ecommerce.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.Entity.entityProduct;
import com.ecommerce.demo.Repository.repositoryProduct;

@Service
public class serviceProduct {
    
    @Autowired
    private repositoryProduct repositoryProduct;

    public List<entityProduct> getAllProducts(){
        return repositoryProduct.findAll();
    }

    public entityProduct getProductById(long id){
        return repositoryProduct.findById(id).orElse(null);
    }

    public void postDeleteProduct(long id){
        repositoryProduct.deleteById(id);
        return;
    }

    public entityProduct postAddProduct(entityProduct product){
        return repositoryProduct.save(product);
    }

    public entityProduct postUpdateProduct(entityProduct product){
        repositoryProduct.deleteById(product.getId());
        return repositoryProduct.save(product);
    }

}

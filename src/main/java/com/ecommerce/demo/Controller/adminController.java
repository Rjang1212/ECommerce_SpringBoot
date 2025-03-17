package com.ecommerce.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.Entity.entityProduct;
import com.ecommerce.demo.Service.serviceProduct;

@RestController
@RequestMapping("/api/admin/products")
public class adminController {

    @Autowired
    private serviceProduct serviceProduct;

    @PostMapping("/{id}/delete")
    public String postDeleteProduct(@PathVariable long id) {
        try {
            serviceProduct.postDeleteProduct(id);
            return "Deleted successfully";
        } catch (Exception e) {
            return "Fail to delete";
        }
    }

    @PostMapping("/add")
    public ResponseEntity<entityProduct> postAddProduct(@RequestBody entityProduct product) {
        entityProduct savedEntityProduct = serviceProduct.postAddProduct(product);
        return ResponseEntity.ok(savedEntityProduct);
    }

    @PostMapping("/update")
    public ResponseEntity<entityProduct> postUpdateProduct(@RequestBody entityProduct product){
        entityProduct updateEntityProduct = serviceProduct.postUpdateProduct(product);
        return ResponseEntity.ok(updateEntityProduct);
    }
}

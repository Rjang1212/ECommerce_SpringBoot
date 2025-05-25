package com.ecommerce.demo.Service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.demo.Entity.entityProduct;
import com.ecommerce.demo.Entity.entityUser;
import com.ecommerce.demo.Entity.entityCart;
import com.ecommerce.demo.Repository.repositoryCart;
import com.ecommerce.demo.Repository.repositoryProduct;
import com.ecommerce.demo.Repository.repositoryUser;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;

@Service
public class servicePayment {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Autowired
    private repositoryUser repositoryUser;

    @Autowired
    private repositoryCart repositoryCart;

    @Autowired
    private repositoryProduct repositoryProduct;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public Long getAmount() {
        Long amount = 0L;

        Object princial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if(princial instanceof entityProduct){
            username = ((entityUser) princial).getName();
        } else {
            username = princial.toString();
        }

        entityUser currUser = repositoryUser.findByName(username);
        Long userId = currUser.getId();

        List<entityCart> cartItems = repositoryCart.findByUserId(userId);
        for (entityCart item : cartItems) {
            entityProduct product = repositoryProduct.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProductId()));
            amount += product.getPrice() * item.getQuantity() * 100;
        }
        
        return amount;
    }

    public Session createCheckoutSession(String successUrl, String cancelUrl, Long amount) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(successUrl)
            .setCancelUrl(cancelUrl)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                        .setUnitAmount(amount)
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Product Name")
                                .build()
                        )
                        .build()
                    )
                    .build()
            )
            .build();

        return Session.create(params);
    }
}

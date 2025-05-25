package com.ecommerce.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.checkout.Session;
import com.ecommerce.demo.Service.servicePayment;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/api/checkout")
public class paymentController {
    @Autowired
    private servicePayment stripeService;

    @GetMapping
    public String createCheckoutSession() {
        try {

            Long amount = stripeService.getAmount();
            
            Session session = stripeService.createCheckoutSession(
                "http://localhost:9090/success",
                "http://localhost:9090/cancel",
                amount
            );
            return session.getUrl();
        } catch (StripeException e) {
            e.printStackTrace();
            return "Error creating Stripe session: " + e.getMessage();
        }
    }
}

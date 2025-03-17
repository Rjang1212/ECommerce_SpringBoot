package com.ecommerce.demo.Service;

import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Value;

@Service
public class servicePayment {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public servicePayment() {
        Stripe.apiKey = stripeApiKey;
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

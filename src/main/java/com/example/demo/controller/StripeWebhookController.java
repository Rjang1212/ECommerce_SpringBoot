package com.example.demo.controller;

import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@RestController
@RequestMapping("/api/webhook")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(HttpServletRequest request) {
        String payload;
        try (InputStream inputStream = request.getInputStream();
             Scanner scanner = new Scanner(inputStream, "UTF-8")) {
            payload = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload");
        }

        String sigHeader = request.getHeader("Stripe-Signature");
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Deserialize the event data object
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        if (dataObjectDeserializer.getObject().isPresent()) {
            Object stripeObject = dataObjectDeserializer.getObject().get();

            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    // Handle successful payment here
                    handlePaymentSuccess(paymentIntent);
                    break;
                // Handle other event types
                default:
                    // Unexpected event type
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unhandled event type");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deserialize event data");
        }

        return ResponseEntity.ok("Event received");
    }

    private void handlePaymentSuccess(PaymentIntent paymentIntent) {
        // Implement your logic here, e.g., update order status, send confirmation email, etc.
        System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
        // Example: Update order status in the database
        // orderService.updateOrderStatus(paymentIntent.getId(), "PAID");
    }
}

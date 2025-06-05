package payment.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import payment.demo.DTO.OrderRequest;
import com.stripe.model.checkout.Session;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;

@Service
public class paymentConsumer {
    
    @Autowired
    private repositoryProduct productRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(paymentConsumer.class);
    
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }
 

    @KafkaListener(topics="order-payment-events", groupId = "payment-group")
    public void consume(OrderRequest orderRequest) {

        Long productId = Long.parseLong(orderRequest.getProductId());
        Optional<entityProduct> product = productRepository.findById(productId);

        if(product.isPresent()) {
            Long amount = product.get().getPrice() * 100L;

            try {
                Session session = createCheckoutSession(
                    "http://localhost:9090/success",
                    "http://localhost:9090/cancel",
                    amount
                );

                logger.info("Make payment: "+session.getUrl());
                kafkaTemplate.send("payment-events", "PaymentUrl: "+session.getUrl());
            } catch (StripeException e) {
                logger.error("Exception while making payment", e);
                // Handle exception as needed
            }

        }
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

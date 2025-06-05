package inventory.demo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import inventory.demo.repositoryProduct;
import inventory.demo.entityProduct;
import inventory.demo.DTO.OrderRequest;

import java.util.Optional;

@Service
public class OrderEventConsumer {

    @Autowired
    private repositoryProduct productRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);
    
    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void consumeOrderEvent(OrderRequest orderRequest) {

        Long productId = Long.parseLong(orderRequest.getProductId());
        Optional<entityProduct> product = productRepository.findById(productId);

        String responseEvent;
        if(product.isEmpty()) {
            responseEvent = String.format("{\"{\"orderId\": \"%s\", \"status\": \"StockFailed\"}", orderRequest.getUserId());
        } else {
            responseEvent = String.format("{\"orderId\": \"%s\", \"status\": \"StockValidated\"}", orderRequest.getUserId());
        }

        logger.info(responseEvent);
        kafkaTemplate.send("inventory-events", responseEvent);
    }
}

package order.service.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import order.service.order.DTO.OrderRequest;

@Service
public class OrderController {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private KafkaTemplate<String, OrderRequest> kafkaTemplate;
    private OrderRequest Order;

    @PostConstruct
    public void init() {
        logger.info("Entered OrderController");
    }

    public OrderController(KafkaTemplate<String, OrderRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "root-events", groupId = "json-group2", containerFactory = "orderRequestKafkaListenerContainerFactory")
    public void consumeRootEvent(OrderRequest orderRequest) {
        Order = orderRequest;
        kafkaTemplate.send("order-events", Order);
        
        logger.info("Order event sent successfully");
    }

    @KafkaListener(topics = "inventory-events", groupId = "string-group", containerFactory = "stringKafkaListenerContainerFactory")
    public void consumeInventoryStatus(String message) {
        if(message.contains("StockValidated")) {
            logger.info("Product exists, sending event message to payment service");
            kafkaTemplate.send("order-payment-events", Order);
        } else {
            logger.info("Product is not available currently!");
        }
    }

    @KafkaListener(topics = "payment-events", groupId = "string-group", containerFactory = "stringKafkaListenerContainerFactory")
    public void consumePaymentStatus(String message) {

        if(message.contains("")) {
            logger.info("Pay thy price, Please visit again");
        } else {
            logger.info("Can not make the payment, Sorry!");
        }
    }
}

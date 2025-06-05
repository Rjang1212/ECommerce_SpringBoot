package order.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {

	private static final Logger logger = LoggerFactory.getLogger(OrderApplication.class);

	public static void main(String[] args) {

		logger.info("Starting Order Service");
		SpringApplication.run(OrderApplication.class, args);
	}
}


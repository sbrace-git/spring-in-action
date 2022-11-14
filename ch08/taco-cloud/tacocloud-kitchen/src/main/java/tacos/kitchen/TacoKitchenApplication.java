package tacos.kitchen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tacos.kitchen.messaging.domain.Order;
import tacos.kitchen.messaging.receiver.OrderReceiver;

@Slf4j
@SpringBootApplication
public class TacoKitchenApplication {
    public static void main(String[] args) {
        SpringApplication.run(TacoKitchenApplication.class, args);
    }

    @Bean
    public CommandLineRunner receive(@Qualifier("rabbitOrderReceiver") OrderReceiver orderReceiver) {
        return args -> {
            log.info("receive order start");
            Order order = orderReceiver.receiveOrder();
            log.info("receive order = {}", order);
        };
    }
}

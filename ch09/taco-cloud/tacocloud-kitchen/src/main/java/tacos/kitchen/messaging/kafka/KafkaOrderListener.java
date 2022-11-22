package tacos.kitchen.messaging.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tacos.kitchen.messaging.domain.Order;

@Slf4j
//@Component
public class KafkaOrderListener {

    @KafkaListener(topics = "kafka.tacocloud.order")
    public void handle(Order order, ConsumerRecord<String, Order> record) {
        log.info("Received from partition {} with timestamp {}", record.partition(), record.timestamp());
        log.info("handle order = {}", order);
        log.info("handle record = {}", record);
    }
}

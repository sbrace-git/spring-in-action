package tacos.kitchen.messaging.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tacos.kitchen.messaging.domain.Order;

@Slf4j
@Component
public class RabbitOrderListener {

    @RabbitListener(queues = "tacocloud.order")
    public void rabbitReceiveOrder(Order order) {
        log.info("rabbitReceiveOrder order = {}", order);
    }

}

package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import tacos.kitchen.messaging.domain.Order;
import tacos.kitchen.messaging.receiver.OrderReceiver;

//@Component("rabbitOrderReceiver")
public class RabbitOrderReceiver implements OrderReceiver {

    private final RabbitTemplate rabbitTemplate;


    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Order receiveOrder() {
        return rabbitTemplate.receiveAndConvert("tacocloud.order",
                ParameterizedTypeReference.forType(Order.class));
    }
}

package tacos.messaging.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import tacos.messaging.service.OrderMessagingService;
import tacos.model.Order;

@Slf4j
@Service("rabbitOrderMessagingService")
public class RabbitOrderMessagingServiceImpl implements OrderMessagingService {

    private RabbitTemplate rabbitTemplate;

    public RabbitOrderMessagingServiceImpl(RabbitTemplate rabbitTemplate) {
        log.info("RabbitOrderMessagingServiceImpl init");
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
        rabbitTemplate.convertAndSend("tacocloud.order", order, this::addOrderSource);
    }

    private Message addOrderSource(Message message) {
        MessageProperties messageProperties = message.getMessageProperties();
        messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
        return message;
    }
}

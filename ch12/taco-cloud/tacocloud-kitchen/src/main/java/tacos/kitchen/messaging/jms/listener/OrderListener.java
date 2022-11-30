package tacos.kitchen.messaging.jms.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.kitchen.messaging.domain.Order;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
@Component
public class OrderListener {

    private final MessageConverter messageConverter;

    public OrderListener(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder1(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrder1 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder2(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrder2 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder3(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrder3 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder4(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrder4 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic1(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrderTopic1 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic2(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrderTopic2 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic3(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrderTopic3 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic4(Message message) throws JMSException {
        Order order = (Order) messageConverter.fromMessage(message);
        log.info("receiveOrderTopic4 = {}", order);
    }

}

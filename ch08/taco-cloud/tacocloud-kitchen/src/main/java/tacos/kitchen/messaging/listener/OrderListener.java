package tacos.kitchen.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tacos.kitchen.messaging.domain.Order;

import javax.jms.Message;

@Slf4j
@Component
public class OrderListener {

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder1(Message order) {
        log.info("receiveOrder1 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder2(Message order) {
        log.info("receiveOrder2 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder3(Message order) {
        log.info("receiveOrder3 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.queue", containerFactory = "queueListenerFactory")
    public void receiveOrder4(Message order) {
        log.info("receiveOrder4 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic1(Message order) {
        log.info("receiveOrderTopic1 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic2(Message order) {
        log.info("receiveOrderTopic2 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic3(Message order) {
        log.info("receiveOrderTopic3 = {}", order);
    }

    @JmsListener(destination = "tacocloud.order.topic", containerFactory = "topicListenerFactory")
    public void receiveOrderTopic4(Message order) {
        log.info("receiveOrderTopic4 = {}", order);
    }

}

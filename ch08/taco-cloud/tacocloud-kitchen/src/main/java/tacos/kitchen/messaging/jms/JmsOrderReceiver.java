package tacos.kitchen.messaging.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.kitchen.messaging.domain.Order;
import tacos.kitchen.messaging.receiver.OrderReceiver;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
@Component
public class JmsOrderReceiver implements OrderReceiver {

    private JmsTemplate jmsTemplate;

    private MessageConverter messageConverter;

    public JmsOrderReceiver(JmsTemplate jmsTemplate, MessageConverter messageConverter) {
        this.jmsTemplate = jmsTemplate;
        this.messageConverter = messageConverter;
    }

//    @Override
//    public Order receiveOrder() {
//        return (Order) jmsTemplate.receiveAndConvert();
//    }

    @Override
    public Order receiveOrder() {
        Message receive = jmsTemplate.receive("tacocloud.order.queue");
        try {
            String x_order_source = receive.getStringProperty("X_ORDER_SOURCE");
            log.info("receiveOrder x_order_source = {}", x_order_source);
            return (Order) messageConverter.fromMessage(receive);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

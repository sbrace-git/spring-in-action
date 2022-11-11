package tacos.messaging.service.impl;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.messaging.service.OrderMessagingService;
import tacos.model.Order;

import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingServiceImpl implements OrderMessagingService {

    private JmsTemplate jmsTemplate;

    public JmsOrderMessagingServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        jmsTemplate.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}

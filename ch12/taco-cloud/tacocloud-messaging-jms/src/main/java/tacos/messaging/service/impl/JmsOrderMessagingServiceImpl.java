package tacos.messaging.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.messaging.service.OrderMessagingService;
import tacos.model.Order;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingServiceImpl implements OrderMessagingService {

    private JmsTemplate jmsTemplate;

    private Destination orderQueue;

    private Destination orderTopic;

    public JmsOrderMessagingServiceImpl(JmsTemplate jmsTemplate,
                                        @Qualifier("orderQueue") Destination orderQueue,
                                        @Qualifier("orderTopic") Destination orderTopic) {
        this.jmsTemplate = jmsTemplate;
        this.orderQueue = orderQueue;
        this.orderTopic = orderTopic;
    }

    @Override
    public void sendOrder(Order order) {
        jmsTemplate.convertAndSend(orderQueue, order, this::addOrderSource);
        jmsTemplate.convertAndSend(orderTopic, order, this::addOrderSource);
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}

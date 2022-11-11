package tacos.tilchen.messaging.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tacos.tilchen.messaging.domain.Order;
import tacos.tilchen.messaging.receiver.OrderReceiver;

@Component
public class JmsOrderReceiver implements OrderReceiver {

    private JmsTemplate jmsTemplate;

    public JmsOrderReceiver(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Order receiveOrder() {
        return (Order) jmsTemplate.receiveAndConvert();
    }
}

package tacos.tilchen.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tacos.tilchen.messaging.domain.Order;

@Slf4j
@Component
public class OrderListener {

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        log.info("receiveOrder = {}", order);
    }
}

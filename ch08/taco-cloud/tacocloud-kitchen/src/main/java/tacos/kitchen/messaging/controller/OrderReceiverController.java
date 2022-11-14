package tacos.kitchen.messaging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.kitchen.messaging.domain.Order;
import tacos.kitchen.messaging.receiver.OrderReceiver;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderReceiverController {

    private final OrderReceiver orderReceiver;

    public OrderReceiverController(@Qualifier("rabbitOrderReceiver") OrderReceiver orderReceiver) {
        this.orderReceiver = orderReceiver;
    }

    @RequestMapping("/receive")
    public String receiveOrder(Model model) {
        Order order = orderReceiver.receiveOrder();
        log.info("receiveOrder order = {}", order);
        if (order != null) {
            model.addAttribute("order", order);
            return "receiveOrder";
        }
        return "noOrder";
    }

}

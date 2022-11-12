package tacos.tilchen.messaging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.tilchen.messaging.domain.Order;
import tacos.tilchen.messaging.receiver.OrderReceiver;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderReceiverController {

    private final OrderReceiver orderReceiver;

    public OrderReceiverController(OrderReceiver orderReceiver) {
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

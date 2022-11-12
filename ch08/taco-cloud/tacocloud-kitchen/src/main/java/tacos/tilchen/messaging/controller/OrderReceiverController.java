package tacos.tilchen.messaging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.tilchen.messaging.domain.Order;
import tacos.tilchen.messaging.receiver.OrderReceiver;

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
        if (order != null) {
            model.addAttribute("order",order);
            return "receiveOrder";
        }
        return "noOrder";
    }

}

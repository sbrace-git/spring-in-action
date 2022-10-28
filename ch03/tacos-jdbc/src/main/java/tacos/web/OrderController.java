package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.model.Order;
import tacos.model.Taco;
import tacos.repository.OrderRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, HttpSession httpSession) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("processOrder Order submitted: {}", order);
        Taco taco = (Taco) httpSession.getAttribute("taco");
        order.setTacos(Collections.singletonList(taco));
        Order save = orderRepository.save(order);
        log.info("processOrder save = {}", save);
        return "redirect:/";
    }

}

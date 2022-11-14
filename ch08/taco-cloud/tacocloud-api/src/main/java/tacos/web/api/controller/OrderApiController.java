package tacos.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tacos.messaging.service.OrderMessagingService;
import tacos.model.Order;
import tacos.repository.OrderRepository;
import tacos.web.api.property.OrderProperties;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderApiController {

    private OrderRepository orderRepository;

    private OrderProperties orderProperties;

    private OrderMessagingService orderMessagingService;

    public OrderApiController(OrderRepository orderRepository, OrderProperties orderProperties,
                              @Qualifier("rabbitOrderMessagingService") OrderMessagingService orderMessagingService) {
        this.orderRepository = orderRepository;
        this.orderProperties = orderProperties;
        this.orderMessagingService = orderMessagingService;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Order> allOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        Order save = orderRepository.save(order);
        orderMessagingService.sendOrder(save);
        return save;
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody Order patch) {
        Order order = orderRepository.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryState());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}

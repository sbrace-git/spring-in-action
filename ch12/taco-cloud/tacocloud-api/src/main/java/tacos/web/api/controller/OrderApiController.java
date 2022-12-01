package tacos.web.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.messaging.service.OrderMessagingService;
import tacos.model.Order;
import tacos.repository.OrderRepository;
import tacos.web.api.property.OrderProperties;

@Slf4j
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderApiController {

    private OrderRepository orderRepository;

    private OrderProperties orderProperties;

    private OrderMessagingService orderMessagingService;

    public OrderApiController(OrderRepository orderRepository, OrderProperties orderProperties,
                              @Qualifier("jmsOrderMessagingServiceImpl") OrderMessagingService orderMessagingService
//                              @Qualifier("rabbitOrderMessagingService") OrderMessagingService orderMessagingService
//                              @Qualifier("kafkaOrderMessagingService") OrderMessagingService orderMessagingService
    ) {
        this.orderRepository = orderRepository;
        this.orderProperties = orderProperties;
        this.orderMessagingService = orderMessagingService;
    }

    @GetMapping(produces = "application/json")
    public Flux<Order> allOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> postOrder(@RequestBody Order order) {
        orderMessagingService.sendOrder(order);
        Mono<Order> insert = orderRepository.insert(order);
        return insert;
    }

    @PostMapping(path = "fromEmail", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void postOrderFromEmail(@RequestBody Object emailOrder) {
        log.info("postOrderFromEmail emailOrder = {}", emailOrder);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Mono<Order> patchOrder(@PathVariable("orderId") String orderId,
                            @RequestBody Order patch) {
        Order order = orderRepository.findById(orderId).block();
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
    public void deleteOrder(@PathVariable("orderId") String orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}

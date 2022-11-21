package tacos.messaging.service;

import tacos.model.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}

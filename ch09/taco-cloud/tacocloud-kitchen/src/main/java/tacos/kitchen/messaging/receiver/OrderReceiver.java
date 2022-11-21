package tacos.kitchen.messaging.receiver;

import tacos.kitchen.messaging.domain.Order;

public interface OrderReceiver {
    Order receiveOrder();
}

package tacos.tilchen.messaging.receiver;

import tacos.tilchen.messaging.domain.Order;

public interface OrderReceiver {
    Order receiveOrder();
}

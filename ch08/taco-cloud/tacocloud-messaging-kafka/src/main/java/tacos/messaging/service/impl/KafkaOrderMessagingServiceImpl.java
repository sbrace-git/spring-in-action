package tacos.messaging.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import tacos.messaging.service.OrderMessagingService;
import tacos.model.Order;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service("kafkaOrderMessagingService")
public class KafkaOrderMessagingServiceImpl implements OrderMessagingService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaOrderMessagingServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send("kafka.tacocloud.order", order);
        try {
            SendResult<String, Object> stringOrderSendResult = send.get();
            log.info("sendOrder stringOrderSendResult = {}", stringOrderSendResult);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

package tacos.email;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tacos.email.domain.Order;
import tacos.email.properties.ApiProperties;

@Component
public class OrderSubmitMessageHandler
        implements GenericHandler<Order> {

    private RestTemplate rest;
    private ApiProperties apiProps;

    public OrderSubmitMessageHandler(ApiProperties apiProps, RestTemplate rest) {
        this.apiProps = apiProps;
        this.rest = rest;
    }

    @Override
    public Object handle(Order payload, MessageHeaders headers) {
        rest.postForObject(apiProps.getUrl(), payload, String.class);
        return null;
    }
}

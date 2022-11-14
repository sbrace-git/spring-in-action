package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqMessagingConfig {
    @Bean
    public Jackson2JsonMessageConverter rabbitMqMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

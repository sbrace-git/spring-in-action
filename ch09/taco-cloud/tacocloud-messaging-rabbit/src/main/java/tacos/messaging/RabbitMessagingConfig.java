package tacos.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMessagingConfig {

    @Bean("rabbitMessageConverter")
    public Jackson2JsonMessageConverter messageConverter() {
        log.info("messageConverter new Jackson2JsonMessageConverter()");
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("tacocloud.order");
    }

    @Bean
    public Queue rabbitOrderQueue() {
        return new Queue("tacocloud.order");
    }

    @Bean
    public Object bindingDirectExchangeOrderQueue() {
        return BindingBuilder.bind(rabbitOrderQueue())
                .to(directExchange())
                .withQueueName();
    }

}

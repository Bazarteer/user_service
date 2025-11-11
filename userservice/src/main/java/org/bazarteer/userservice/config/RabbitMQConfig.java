package org.bazarteer.userservice.config;



import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "product-exchange";
    public static final String QUEUE_PUBLISHED = "product-published-queue";
    public static final String ROUTING_KEY_PUBLISHED = "product.published";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue publishedQueue() {
        return new Queue(QUEUE_PUBLISHED);
    }

    // za vec exchange, queue in posledično bindings samo definiraj nove beane
    
    @Bean
    Binding publishedBinding(Queue publishedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(publishedQueue).to(exchange).with(ROUTING_KEY_PUBLISHED);
    }
    
    //TODO razmisli ce bi bilo bolje za format uporabiti proto
    @Bean
    Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean 
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    //TODO to je pac testni consumer primerek treba bo zamenjat samo za referenco je
}

package org.bazarteer.userservice.service;

import org.bazarteer.userservice.config.RabbitMQConfig;
import org.bazarteer.userservice.model.ProductPublishedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {
    
    @Autowired
    private UserService userService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PUBLISHED)
    public void consume(ProductPublishedMessage message) {
        userService.handleProductPublished(message);
    }
}

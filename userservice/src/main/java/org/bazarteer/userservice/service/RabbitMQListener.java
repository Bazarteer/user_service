package org.bazarteer.userservice.service;

import org.bazarteer.userservice.config.RabbitMQConfig;
import org.bazarteer.userservice.model.ProductPublishedMessage;
import org.bazarteer.userservice.model.OrderPlacedMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {
    
    @Autowired
    private UserService userService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PUBLISHED)
    public void consumeProductPublished(ProductPublishedMessage message) {
        userService.handleProductPublished(message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER_PLACED)
    public void consumeOrderPlaced(OrderPlacedMessage message) {
        userService.handleOrderPlaced(message);
    }
}

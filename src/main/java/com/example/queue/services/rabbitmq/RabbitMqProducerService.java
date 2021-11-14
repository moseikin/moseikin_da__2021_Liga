package com.example.queue.services.rabbitmq;

import com.example.queue.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource(value = {"classpath:rabbitmq.properties"})
@PropertySource(value = {"classpath:queue.properties"})
public class RabbitMqProducerService {

    @Value("${orderExchange}")
    private String orderExchange;

    @Value("${orderQueue}")
    private String orderQueue;

    @Value(value = "${millisToConfirm}")
    private int millisToConfirm;

    private final AmqpTemplate template;

    public void sendOrder(String orderId) {
        template.convertAndSend(orderExchange, orderQueue, orderId, message -> {
            System.out.println("Время отправки сообщения: " + System.currentTimeMillis());
            message.getMessageProperties().setExpiration(String.valueOf(millisToConfirm));
            return message;
        });
    }
}
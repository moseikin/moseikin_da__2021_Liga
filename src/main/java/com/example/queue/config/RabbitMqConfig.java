package com.example.queue.config;

import com.example.queue.services.rabbitmq.RabbitMqConsumerService;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value = {"classpath:rabbitmq.properties"})
public class RabbitMqConfig {

    @Value("${orderExchange}")
    private String orderExchange;

    @Value("${orderQueue}")
    private String orderQueue;

    @Value(value = "${timeoutQueue}")
    private String timeoutQueue;

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(orderExchange,true,false,null);
    }

    @Bean
    public Queue orderQueue() {
        // По истечении тайм-аута сообщение будет перенаправлено в очередь,
        // связанную ключом маршрутизации x-dead-letter, через x-dead-letter-exchange
        Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-dead-letter-exchange", orderExchange);
        arguments.put("x-dead-letter-routing-key", timeoutQueue);
        return new Queue(orderQueue,true,false,false, arguments);
    }

    @Bean
    public Queue orderTimeoutQueue() {
        return new Queue(timeoutQueue,true,false,false);
    }

    @Bean
    public Binding orderQueueBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(orderQueue);
    }

    @Bean
    public Binding  orderTimeoutQueueBinding() {
        return BindingBuilder.bind(orderTimeoutQueue()).to(orderExchange()).with(timeoutQueue);
    }
}
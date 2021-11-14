package com.example.queue.services.rabbitmq;

import com.example.queue.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = {"classpath:rabbitmq.properties"})
@RequiredArgsConstructor
public class RabbitMqConsumerService {

    private final BookingService bookingService;

    @RabbitHandler
    @RabbitListener(queues = "${timeoutQueue}" )
    public void consumeTimeOutQueue(@Payload String bookId){
        bookingService.doAnnullingBook(Long.parseLong(bookId));
    }


}

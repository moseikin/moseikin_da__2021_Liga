package com.example.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class QueueApplication {

    // Исправить:
    // RabbitMQ для того, чтобы при перезагрузке сервера статусы очереди продожали обновляться
        // delayed plugin до сих пор с режиме experimental
    // Поменять логику на библиотеку spring
    // не использовать куки. Токен передавать в заголовках

    public static void main(String[] args) {
        SpringApplication.run(QueueApplication.class, args);
    }



}

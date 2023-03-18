package com.ms.email.conections;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class RabbitMQConnection {

    private static final String NAME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;
    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String nameQueue) {
        return new Queue(nameQueue, true, false,false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(NAME_EXCHANGE);
    }

    private Binding binding(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }
    @PostConstruct
    private void createQueue() {
        Queue queueEmail = this.queue(RabbitMQConstant.QUEUE_EMAIL);

        DirectExchange exchange = this.directExchange();

        Binding bindingQueueEmail = this.binding(queueEmail, exchange);


        this.amqpAdmin.declareQueue(queueEmail);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(bindingQueueEmail);

    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

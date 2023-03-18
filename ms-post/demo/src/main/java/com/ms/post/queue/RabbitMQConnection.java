package com.ms.post.queue;

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
        Queue queueEventTrack = this.queue(RabbitMQConstant.QUEUE_EVENT_TRACK);
        Queue queuePostage = this.queue(RabbitMQConstant.QUEUE_POSTAGE);

        DirectExchange exchange = this.directExchange();

        Binding bindingQueueEmail = this.binding(queueEmail, exchange);
        Binding bindingQueueEventTrack = this.binding(queueEventTrack, exchange);
        Binding bindingQueuePostage = this.binding(queuePostage, exchange);

        this.amqpAdmin.declareQueue(queueEmail);
        this.amqpAdmin.declareQueue(queueEventTrack);
        this.amqpAdmin.declareQueue(queuePostage);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(bindingQueueEmail);
        this.amqpAdmin.declareBinding(bindingQueueEventTrack);
        this.amqpAdmin.declareBinding(bindingQueuePostage);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

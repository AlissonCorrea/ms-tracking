package com.ms.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.post.dto.EmailDto;
import com.ms.post.dto.PostageDto;
import com.ms.post.queue.RabbitMQConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessageToQueueEmail(EmailDto email) throws JsonProcessingException {
        this.sendMessageToQueue(RabbitMQConstant.QUEUE_EMAIL, email);
    }

    public void sendMessageToQueueEventTrack(PostageDto postage) throws JsonProcessingException {
        this.sendMessageToQueue(RabbitMQConstant.QUEUE_EVENT_TRACK, postage);
    }

    public void sendMessageToQueue(String nameQueue, Object object) throws JsonProcessingException {

        String transactionJson = objectMapper.writeValueAsString(object);

        Message message = MessageBuilder.withBody(transactionJson.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).build();

        rabbitTemplate.convertAndSend(nameQueue, message);
    }
}

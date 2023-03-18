package com.ms.post.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.post.dto.PostageDto;
import com.ms.post.mapper.PostageModelToEmailPostageMapper;
import com.ms.post.queue.RabbitMQConstant;
import com.ms.post.service.PostageService;
import com.ms.post.service.RabbitMQService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostageConsumer {

    @Autowired
    PostageService postageService;

    @Autowired
    private RabbitMQService rabbitMQService;

    private final PostageModelToEmailPostageMapper postageModelToEmailPostageMapper;

    @RabbitListener(queues = RabbitMQConstant.QUEUE_POSTAGE)
    public void consumer(@Payload PostageDto postageDto) throws JsonProcessingException {
        var postageModel = postageService.findByTrackingCode(postageDto.getTrackingCode());

        var postageModelResult = postageService.alterStatus(postageModel, postageDto.getStatus());

        var email = postageModelToEmailPostageMapper.mapper(postageModelResult);
        rabbitMQService.sendMessageToQueueEmail(email);

        System.out.println(postageModelResult);
    }
}

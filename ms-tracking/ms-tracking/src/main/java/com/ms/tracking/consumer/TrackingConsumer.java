package com.ms.tracking.consumer;

import com.ms.tracking.conections.queue.RabbitMQConstant;
import com.ms.tracking.dto.PostageDto;
import com.ms.tracking.dto.TrackingDto;
import com.ms.tracking.model.TrackingModel;
import com.ms.tracking.service.TrackingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TrackingConsumer {

    @Autowired
    TrackingService trackingService;

    @RabbitListener(queues = RabbitMQConstant.QUEUE_EVENT_TRACK)
    public void consumer(@Payload PostageDto postageDto) {

        TrackingModel trackingModel = new TrackingModel();
        trackingModel.setTrackingCode(postageDto.getTrackingCode());
        trackingModel.setStatus(postageDto.getStatus());
        trackingModel.setLocation("Armazém - Transportadora/SC");
        trackingModel.setMessage("Objeto postado na transportadora");

        LocalTime hourNow = LocalTime.now();
        trackingModel.setHour(hourNow);

        LocalDate dateNow = LocalDate.now();
        trackingModel.setDate(dateNow);

        var trackingResult = trackingService.insert(trackingModel);

        System.out.println(trackingResult);
    }
}

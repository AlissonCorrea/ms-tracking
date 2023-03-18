package com.ms.tracking.mapper;

import com.ms.tracking.dto.PostageDto;
import com.ms.tracking.model.TrackingModel;
import org.springframework.stereotype.Component;

@Component
public class PostageDtoToTrackingModel {

    public TrackingModel mapper(PostageDto postageDto) {
        TrackingModel trackingModel = new TrackingModel();
        trackingModel.setTrackingCode(postageDto.getTrackingCode());
        trackingModel.setStatus(postageDto.getStatus());
        trackingModel.setLocation("Armazém - Transportadora/SC");
        trackingModel.setMessage("Objeto postado na transportadora");
        return trackingModel;
    }
}

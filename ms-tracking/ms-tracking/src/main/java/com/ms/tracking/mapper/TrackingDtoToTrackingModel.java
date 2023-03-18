package com.ms.tracking.mapper;

import com.ms.tracking.dto.TrackingDto;
import com.ms.tracking.model.TrackingModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TrackingDtoToTrackingModel {

    public TrackingModel mapper(TrackingDto trackingDto) {
        TrackingModel trackingModel = new TrackingModel();
        BeanUtils.copyProperties(trackingDto, trackingModel);
        return trackingModel;
    }
}

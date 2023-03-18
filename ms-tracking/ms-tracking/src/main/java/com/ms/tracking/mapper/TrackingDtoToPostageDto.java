package com.ms.tracking.mapper;

import com.ms.tracking.dto.PostageDto;
import com.ms.tracking.dto.TrackingDto;
import org.springframework.stereotype.Component;

@Component
public class TrackingDtoToPostageDto {

    public PostageDto mapper(TrackingDto trackingDto) {
        var postageDto = new PostageDto();
        postageDto.setTrackingCode(trackingDto.getTrackingCode());
        postageDto.setStatus(trackingDto.getStatus());
        return postageDto;
    }
}

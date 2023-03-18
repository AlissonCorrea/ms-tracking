package com.ms.tracking.dto;

import com.ms.tracking.enums.StatusPostageEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostageDto {
    private String trackingCode;

    private String zipCodeOrigin;

    private String zipCodeDestination;

    private String emailDestination;

    private Long length;

    private Long height;

    private Long width;

    private Float weight;

    private BigDecimal value;

    private StatusPostageEnum status;
}

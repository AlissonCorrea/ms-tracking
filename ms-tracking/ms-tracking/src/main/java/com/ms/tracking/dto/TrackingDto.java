package com.ms.tracking.dto;

import com.ms.tracking.enums.StatusPostageEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TrackingDto {

    private String location;

    private LocalDate date;

    private LocalTime hour;

    private StatusPostageEnum status;

    private String message;

    private String trackingCode;
}

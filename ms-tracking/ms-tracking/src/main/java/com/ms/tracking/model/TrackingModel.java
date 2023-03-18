package com.ms.tracking.model;

import com.ms.tracking.enums.StatusPostageEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tracking")
public class TrackingModel extends RepresentationModel<TrackingModel>  implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long id;

    private String location;

    private LocalDate date;

    private LocalTime hour;

    private StatusPostageEnum status;

    private String message;

    private String trackingCode;
}

package com.ms.post.model;

import com.ms.post.enums.StatusPostageEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "postage")
public class PostageModel extends RepresentationModel<PostageModel> implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long id;

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

package com.ms.tracking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.tracking.dto.TrackingDto;
import com.ms.tracking.mapper.TrackingDtoToPostageDto;
import com.ms.tracking.mapper.TrackingDtoToTrackingModel;
import com.ms.tracking.model.TrackingModel;
import com.ms.tracking.service.RabbitMQService;
import com.ms.tracking.service.TrackingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/tracking",produces = { "application/json" })
@AllArgsConstructor
public class TrackingController {

    @Autowired
    TrackingService trackingService;

    @Autowired
    private RabbitMQService rabbitMQService;

    private final TrackingDtoToTrackingModel trackingDtoToTrackingModel;

    private final TrackingDtoToPostageDto trackingDtoToPostageDto;


    @PostMapping("/{trackingCode}")
    public ResponseEntity<TrackingModel> insertTracking(
            @PathVariable("trackingCode") String trackingCode,
            @RequestBody TrackingDto trackingDto
    ) throws JsonProcessingException {

        trackingDto.setTrackingCode(trackingCode);

        var trackingModel = trackingDtoToTrackingModel.mapper(trackingDto);

        TrackingModel trackingLast = trackingService.findLastEventByTrackingCode(trackingModel.getTrackingCode());

        if (trackingLast != null && trackingLast.getStatus() != trackingDto.getStatus()) {
            rabbitMQService.sendMessageToQueuePostage(trackingDtoToPostageDto.mapper(trackingDto));
        }

        trackingService.insert(trackingModel);

        return new ResponseEntity<>(trackingModel, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<TrackingModel>> findAllTrackingModel() {

        List<TrackingModel> trackingList = trackingService.findAll();

        if (trackingList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (TrackingModel trackingModel : trackingList) {
                long id = trackingModel.getId();
                trackingModel.add(linkTo(methodOn(TrackingController.class).findByIdTrackingModel(id)).withSelfRel());
            }
            return new ResponseEntity<List<TrackingModel>>(trackingList, HttpStatus.OK);
        }
    }

    @GetMapping("/trackingCode/{trackingCode}")
    public ResponseEntity<List<TrackingModel>> findAllByTrackingCodeTrackingModel(@PathVariable(value = "trackingCode") String trackingCode) {

        List<TrackingModel> trackingList = trackingService.findAllByTrackCode(trackingCode);

        if (trackingList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (TrackingModel trackingModel : trackingList) {
                long id = trackingModel.getId();
                trackingModel.add(linkTo(methodOn(TrackingController.class).findByIdTrackingModel(id)).withSelfRel());
            }
            return new ResponseEntity<List<TrackingModel>>(trackingList, HttpStatus.OK);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<TrackingModel> findByIdTrackingModel(@PathVariable(value = "id") long id) {
        Optional<TrackingModel> trackingModel = trackingService.findById(id);
        if (!trackingModel.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            trackingModel.get().add(linkTo(methodOn(TrackingController.class).findAllTrackingModel()).withRel("Lista de Eventos de Rastreio"));
            return new ResponseEntity<TrackingModel>(trackingModel.get(), HttpStatus.OK);
        }
    }

}
